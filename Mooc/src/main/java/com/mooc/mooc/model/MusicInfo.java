package com.mooc.mooc.model;

import java.util.Date;

public class MusicInfo {
    private String musicid;

    private String musicname;

    private String remark;

    private Integer rankid;

    private Integer ranknum;

    private Date updatetime;

    private String appname;

    public String getMusicid() {
        return musicid;
    }

    public void setMusicid(String musicid) {
        this.musicid = musicid == null ? null : musicid.trim();
    }

    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname == null ? null : musicname.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRankid() {
        return rankid;
    }

    public void setRankid(Integer rankid) {
        this.rankid = rankid;
    }

    public Integer getRanknum() {
        return ranknum;
    }

    public void setRanknum(Integer ranknum) {
        this.ranknum = ranknum;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }
}