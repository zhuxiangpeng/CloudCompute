package com.mooc.mooc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mooc.mooc.mapper.MusicInfoMapper;
import com.mooc.mooc.mapper.MusicRankInfoMapper;
import com.mooc.mooc.mapper.RankInfoMapper;
import com.mooc.mooc.model.MusicInfo;
import com.mooc.mooc.model.MusicRankInfo;
import com.mooc.mooc.model.RankInfo;
import com.mooc.mooc.service.QqService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class QqServiceImpl implements QqService {

    @Resource
    private RankInfoMapper rankInfoMapper;

    @Resource
    private MusicInfoMapper musicInfoMapper;

    @Resource
    private MusicRankInfoMapper musicRankInfoMapper;

    @Override
    public List<MusicInfo> getRankMusic(Integer rankid) throws Exception {
        List<MusicInfo> list = new ArrayList<>();
        list = musicInfoMapper.selectByRankid(rankid);
        String url = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?g_tk=5381&uin=0&format=json&inCharset=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&tpl=3&page=detail&type=top&topid=";
        url = url + rankid + "&_=1512563984096";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpGet httpGet = new HttpGet(url);
            JSONObject jsonResult = null;
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            HttpResponse response = httpclient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonResult = JSONObject.parseObject(result);
            JSONArray jsonArray = jsonResult.getJSONArray("songlist");
            int i = 1;
            for (Object object : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setMusicname(jsonObject.getJSONObject("data").getString("songname"));
                musicInfo.setRemark(jsonObject.getJSONObject("data").getString("albumname"));
                musicInfo.setUpdatetime(new Date());
                musicInfo.setRanknum(i);
                musicInfo.setRankid(rankid);
                musicInfo.setAppname("QQ音乐");
                String inListStr = inMusicList(musicInfo.getMusicname(), list);
                if (inListStr.equals("none")) {
                    musicInfo.setMusicid(UUID.randomUUID().toString().replace("-", ""));
                    musicInfoMapper.insert(musicInfo);
                    list.add(musicInfo);
                } else {
                    musicInfo.setMusicid(inListStr);
                    musicInfoMapper.updateByPrimaryKey(musicInfo);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("调用api获取QQ音乐" + rankid + "榜单下的音乐排行异常：", e);
        }
        return list;
    }

    private String inMusicList(String musicname, List<MusicInfo> list) {
        for (MusicInfo musicInfo : list) {
            if (musicname.equals(musicInfo.getMusicname()) && musicInfo.getAppname().equals("QQ音乐")) {
                return musicInfo.getMusicid();
            }
        }
        return "none";
    }

    @Override
    public List<RankInfo> renovateRankList() throws IOException {
        List<RankInfo> list = new ArrayList<>();
        list = rankInfoMapper.selectAll();
        String url = "https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg?g_tk=5381&uin=0&format=json&inCharset=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&_=1512554796112";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httpPost = new HttpPost(url);
            JSONObject jsonResult = null;
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            HttpResponse response = httpclient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            result = result.substring(result.indexOf("{\"code\""), result.lastIndexOf(");</script></body></html>"));
            jsonResult = JSONObject.parseObject(result);
            JSONArray jsonArray = jsonResult.getJSONObject("data").getJSONArray("topList");
            for (Object object : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                RankInfo rankInfo = new RankInfo();
                rankInfo.setRankid(jsonObject.getInteger("id"));
                rankInfo.setRankname(jsonObject.getString("topTitle"));
                rankInfo.setUpdatetime(new Date());
                rankInfo.setAppname("QQ音乐");
                String inListStr = inRankList(rankInfo.getRankid(), list);
                if (!inListStr.equals("none")) {
                    rankInfo.setId(inListStr);
                    rankInfoMapper.updateByPrimaryKey(rankInfo);
                } else {
                    rankInfo.setId(UUID.randomUUID().toString().replace("-", ""));
                    rankInfoMapper.insert(rankInfo);
                    list.add(rankInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("调用api获取QQ音乐最新榜单异常：", e);
        }
        return list;
    }

    @Override
    public List<MusicRankInfo> getAllRank() {
        musicRankInfoMapper.deleteByApp("QQ音乐");
        List<MusicInfo> musicList = musicInfoMapper.selectByApp("QQ音乐");
        List<RankInfo> rankList = rankInfoMapper.selectByApp("QQ音乐");
        for(MusicInfo musicInfo:musicList){
            MusicRankInfo musicRankInfo = new MusicRankInfo();
            String rankname = getRanknameByRankid(musicInfo, rankList);
            musicRankInfo.setId(UUID.randomUUID().toString().replace("-", ""));
            musicRankInfo.setMusicid(musicInfo.getMusicid());
            musicRankInfo.setMusicname(musicInfo.getMusicname());
            musicRankInfo.setAppname(musicInfo.getAppname());
            musicRankInfo.setRankid(musicInfo.getRankid());
            musicRankInfo.setRankname(rankname);
            int hotnum = computeHotnum(musicRankInfo,musicInfo.getRanknum());
            musicRankInfo.setHotnum(hotnum);
            musicRankInfoMapper.insert(musicRankInfo);
        }
        List<MusicRankInfo> list = musicRankInfoMapper.selectByApp("QQ音乐");
        return list;
    }

    private int computeHotnum(MusicRankInfo musicRankInfo, Integer ranknum) {
        int hotnum = 0;
        switch (musicRankInfo.getRankid()){
            case 17:
                //巅峰榜·日本
                hotnum = 1*(100-ranknum);
                break;
            case 58:
                //说唱榜
                hotnum = 2*(100-ranknum);
                break;
            case 27:
                //巅峰榜·新歌
                hotnum = 3*(100-ranknum);
                break;
            case 36:
                //巅峰榜·K歌金曲
                hotnum = 3*(100-ranknum);
                break;
            case 5:
                //巅峰榜·内地
                hotnum = 2*(100-ranknum);
                break;
            case 26:
                //巅峰榜·热歌
                hotnum = 1*(100-ranknum);
                break;
            case 65:
                //国风热歌榜
                hotnum = 2*(100-ranknum);
                break;
            case 62:
                //飙升榜
                hotnum = 3*(100-ranknum);
                break;
            case 4:
                //巅峰榜·流行指数
                hotnum = 3*(100-ranknum);
                break;
            case 16:
                //巅峰榜·韩国
                hotnum = 1*(100-ranknum);
                break;
            case 64:
                //综艺新歌榜
                hotnum = 1*(100-ranknum);
                break;
            case 67:
                //听歌识曲榜
                hotnum = 1*(100-ranknum);
                break;
            case 61:
                //台湾地区榜
                hotnum = 1*(100-ranknum);
                break;
            case 60:
                //抖音排行榜
                hotnum = 2*(100-ranknum);
                break;
            case 52:
                //巅峰榜·腾讯音乐人原创榜
                hotnum = 1*(100-ranknum);
                break;
            case 74:
                //Q音快手榜
                hotnum = 2*(100-ranknum);
                break;
            case 75:
                //有声榜
                hotnum = 1*(100-ranknum);
                break;
            case 59:
                //香港地区榜
                hotnum = 1*(100-ranknum);
                break;
            case 63:
                //DJ舞曲榜
                hotnum = 2*(100-ranknum);
                break;
            case 28:
                //巅峰榜·网络歌曲
                hotnum = 1*(100-ranknum);
                break;
            case 3:
                //巅峰榜·欧美
                hotnum = 1*(100-ranknum);
                break;
            case 57:
                //电音榜
                hotnum = 1*(100-ranknum);
                break;
            case 29:
                //巅峰榜·影视金曲
                hotnum = 2*(100-ranknum);
                break;
            case 70:
                //达人音乐榜
                hotnum = 1*(100-ranknum);
                break;
            case 72:
                //动漫音乐榜
                hotnum = 1*(100-ranknum);
                break;
            case 73:
                //游戏音乐榜
                hotnum = 1*(100-ranknum);
                break;
            case 201:
                //巅峰榜·MV
                hotnum = 1*(100-ranknum);
                break;
            default:
                break;
        }
        return hotnum;
    }

    private String getRanknameByRankid(MusicInfo musicInfo, List<RankInfo> rankList) {
        for(RankInfo rankInfo:rankList){
            if(rankInfo.getRankid().equals(musicInfo.getRankid())){
                return rankInfo.getRankname();
            }
        }
        return "无榜名";
    }

    private String inRankList(Integer rankid, List<RankInfo> list) {
        for (RankInfo rankInfo : list) {
            if (rankid.equals(rankInfo.getRankid()) && rankInfo.getAppname().equals("QQ音乐")) {
                return rankInfo.getId();
            }
        }
        return "none";
    }
}
