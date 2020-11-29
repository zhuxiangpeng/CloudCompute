package com.mooc.mooc.service;

import com.mooc.mooc.model.MusicInfo;
import com.mooc.mooc.model.MusicRankInfo;
import com.mooc.mooc.model.RankInfo;

import java.io.IOException;
import java.util.List;

public interface QqService {

    public List<MusicInfo> getRankMusic(Integer rankid) throws Exception;

    public List<RankInfo> renovateRankList() throws IOException;

    public List<MusicRankInfo> getAllRank();
}
