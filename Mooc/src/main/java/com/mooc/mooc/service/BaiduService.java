package com.mooc.mooc.service;

import com.mooc.mooc.model.MusicInfo;

import java.util.List;

public interface BaiduService {

    public List<MusicInfo> getRankMusic(Integer rankid) throws Exception;
}
