package com.mooc.mooc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mooc.mooc.mapper.MusicInfoMapper;
import com.mooc.mooc.mapper.MusicRankInfoMapper;
import com.mooc.mooc.mapper.RankInfoMapper;
import com.mooc.mooc.model.MusicInfo;
import com.mooc.mooc.model.MusicRankInfo;
import com.mooc.mooc.model.RankInfo;
import com.mooc.mooc.service.BaiduService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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
public class BaiduServiceImpl implements BaiduService {

    @Resource
    private MusicInfoMapper musicInfoMapper;

    @Resource
    private RankInfoMapper rankInfoMapper;

    @Resource
    private MusicRankInfoMapper musicRankInfoMapper;

    private static final int num = 500;

    @Override
    public List<MusicInfo> getRankMusic(Integer rankid) throws Exception {
        List<MusicInfo> list = new ArrayList<>();
        list = musicInfoMapper.selectByRankid(rankid);
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?size=100&type=";
        url = url + rankid + "&callback=cb_list&_t=1468380543284&format=json&method=baidu.ting.billboard.billList";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpGet httpGet = new HttpGet(url);
            JSONObject jsonResult = null;
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            HttpResponse response = httpclient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            result = result.substring(result.indexOf("{\"song_list\""), result.lastIndexOf(");"));
            jsonResult = JSONObject.parseObject(result);
            JSONArray jsonArray = jsonResult.getJSONArray("song_list");
            int i = 1;
            for (Object object : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setMusicname(jsonObject.getString("title"));
                musicInfo.setRemark(jsonObject.getString("album_title"));
                musicInfo.setUpdatetime(new Date());
                musicInfo.setRanknum(i);
                musicInfo.setRankid(rankid);
                musicInfo.setAppname("百度音乐");
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
            throw new Exception("调用api获取百度音乐" + rankid + "榜单下的音乐排行异常：", e);
        }
        list = musicInfoMapper.selectByRankid(rankid);
        return list;
    }

    @Override
    public List<RankInfo> renovateRankList() {
        List<RankInfo> list = rankInfoMapper.selectByApp("百度音乐");
        list = queryByApp(list);
        return list;
    }

    @Override
    public List<MusicRankInfo> getAllRank() {
        musicRankInfoMapper.deleteByApp("百度音乐");
        List<MusicInfo> musicList = musicInfoMapper.selectByApp("百度音乐");
        List<RankInfo> rankList = rankInfoMapper.selectByApp("百度音乐");
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
            musicRankInfo.setUpdatetime(new Date());
            musicRankInfoMapper.insert(musicRankInfo);
        }
        List<MusicRankInfo> list = musicRankInfoMapper.selectByApp("百度音乐");
        return list;
    }

    private int computeHotnum(MusicRankInfo musicRankInfo, Integer ranknum) {
        int hotnum = 0;
        switch (musicRankInfo.getRankid()){
            case 23:
                //情歌对唱榜
                hotnum = 1*(num-ranknum);
                break;
            case 6:
                //KTV热歌榜
                hotnum = 2*(num-ranknum);
                break;
            case 20:
                //华语金曲榜
                hotnum = 3*(num-ranknum);
                break;
            case 9:
                //雪碧音碰音榜
                hotnum = 1*(num-ranknum);
                break;
            case 14:
                //影视金曲榜
                hotnum = 2*(num-ranknum);
                break;
            case 25:
                //网络歌曲榜
                hotnum = 1*(num-ranknum);
                break;
            case 22:
                //经典老歌榜
                hotnum = 1*(num-ranknum);
                break;
            case 1:
                //新歌榜
                hotnum = 3*(num-ranknum);
                break;
            case 21:
                //欧美金曲榜
                hotnum = 1*(num-ranknum);
                break;
            case 11:
                //摇滚榜
                hotnum = 1*(num-ranknum);
                break;
            case 2:
                //热歌榜
                hotnum = 1*(num-ranknum);
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

    private List<RankInfo> queryByApp(List<RankInfo> list) {
        List<RankInfo> list1 = new ArrayList<>();
        for(RankInfo rankInfo:list){
            if(rankInfo.getAppname().equals("百度音乐")){
                list1.add(rankInfo);
            }
        }
        return list1;
    }

    private String inMusicList(String musicname, List<MusicInfo> list) {
        for (MusicInfo musicInfo : list) {
            if (musicname.equals(musicInfo.getMusicname()) && musicInfo.getAppname().equals("百度音乐")) {
                return musicInfo.getMusicid();
            }
        }
        return "none";
    }
}
