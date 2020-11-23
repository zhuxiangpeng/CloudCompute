package com.mooc.mooc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mooc.mooc.mapper.MusicInfoMapper;
import com.mooc.mooc.mapper.RankInfoMapper;
import com.mooc.mooc.model.MusicInfo;
import com.mooc.mooc.model.RankInfo;
import com.mooc.mooc.service.KugouService;
import org.apache.http.HttpResponse;
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
public class KugouServiceImpl implements KugouService {

    @Resource
    private RankInfoMapper rankInfoMapper;

    @Resource
    private MusicInfoMapper musicInfoMapper;

    @Override
    public List<MusicInfo> getRankMusic(Integer rankid) throws Exception {
        List<MusicInfo> list = new ArrayList<>();
        list = musicInfoMapper.selectByRankid(rankid);
        String url = "http://m.kugou.com/rank/info?rankid=";
        url = url + rankid + "&page=1&json=true";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        //String param1="url=http://m.kugou.com/rank/info?rankid=31312&page=1&json=true";
        try {
            //StringEntity stringEntity = new StringEntity(param1);
            //stringEntity.setContentType("application/x-www-form-urlencoded");
            HttpPost httpPost = new HttpPost(url);
            //httpPost.setEntity(stringEntity);
            JSONObject jsonResult = null;
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            HttpResponse response = httpclient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonResult = JSONObject.parseObject(result);
            JSONArray jsonArray = jsonResult.getJSONObject("songs").getJSONArray("list");
            int i = 1;
            for (Object object : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setMusicname(jsonObject.getString("filename"));
                musicInfo.setRemark(jsonObject.getString("remark"));
                musicInfo.setUpdatetime(new Date());
                musicInfo.setRanknum(i);
                musicInfo.setRankid(rankid);
                musicInfo.setAppname("酷狗音乐");
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
            throw new Exception("调用api获取酷狗音乐" + rankid + "榜单下的音乐排行异常：", e);
        }
        return list;
    }

    private String inMusicList(String musicname, List<MusicInfo> list) {
        for (MusicInfo musicInfo : list) {
            if (musicname.equals(musicInfo.getMusicname()) && musicInfo.getAppname().equals("酷狗音乐")) {
                return musicInfo.getMusicid();
            }
        }
        return "none";
    }

    @Override
    public List<RankInfo> renovateRankList() throws IOException {
        List<RankInfo> list = new ArrayList<>();
        list = rankInfoMapper.selectAll();
        String url = "https://bird.ioliu.cn/v1?url=http://m.kugou.com/rank/list?json=true";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httpPost = new HttpPost(url);
            JSONObject jsonResult = null;
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            HttpResponse response = httpclient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonResult = JSONObject.parseObject(result);
            JSONArray jsonArray = jsonResult.getJSONObject("rank").getJSONArray("list");
            for (Object object : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                RankInfo rankInfo = new RankInfo();
                rankInfo.setRankid(jsonObject.getInteger("rankid"));
                rankInfo.setRankname(jsonObject.getString("rankname"));
                rankInfo.setUpdatetime(new Date());
                rankInfo.setAppname("酷狗音乐");
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
            throw new IOException("调用api获取酷狗音乐最新榜单异常：", e);
        }
        return list;
    }

    private String inRankList(Integer rankid, List<RankInfo> list) {
        for (RankInfo rankInfo : list) {
            if (rankid.equals(rankInfo.getRankid()) && rankInfo.getAppname().equals("酷狗音乐")) {
                return rankInfo.getId();
            }
        }
        return "none";
    }
}
