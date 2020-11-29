package com.mooc.mooc.mapper;

import com.mooc.mooc.model.RankInfo;
import java.util.List;

public interface RankInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(RankInfo record);

    RankInfo selectByPrimaryKey(String id);

    List<RankInfo> selectAll();

    int updateByPrimaryKey(RankInfo record);

    List<RankInfo> selectByApp(String appname);
}