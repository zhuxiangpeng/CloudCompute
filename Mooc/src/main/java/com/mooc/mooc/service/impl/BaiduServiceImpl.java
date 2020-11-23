package com.mooc.mooc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mooc.mooc.mapper.MusicInfoMapper;
import com.mooc.mooc.mapper.RankInfoMapper;
import com.mooc.mooc.model.MusicInfo;
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
        return list;
    }

    @Override
    public List<RankInfo> renovateRankList() {
        List<RankInfo> list = rankInfoMapper.selectAll();
        list = queryByApp(list);
        return list;
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
