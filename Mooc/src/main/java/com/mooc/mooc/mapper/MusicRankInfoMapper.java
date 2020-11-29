package com.mooc.mooc.mapper;

import com.mooc.mooc.model.MusicRankInfo;
import java.util.List;

public interface MusicRankInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MusicRankInfo record);

    MusicRankInfo selectByPrimaryKey(String id);

    List<MusicRankInfo> selectAll();

    int updateByPrimaryKey(MusicRankInfo record);

    List<MusicRankInfo> selectByApp(String appname);

    int deleteByApp(String appname);
}