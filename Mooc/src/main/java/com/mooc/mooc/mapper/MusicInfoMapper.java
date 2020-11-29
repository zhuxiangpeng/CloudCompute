package com.mooc.mooc.mapper;

import com.mooc.mooc.model.MusicInfo;
import java.util.List;

public interface MusicInfoMapper {
    int deleteByPrimaryKey(String musicid);

    int insert(MusicInfo record);

    MusicInfo selectByPrimaryKey(String musicid);

    List<MusicInfo> selectAll();

    List<MusicInfo> selectByRankid(Integer rankid);

    int updateByPrimaryKey(MusicInfo record);

    List<MusicInfo> selectByApp(String appname);
}