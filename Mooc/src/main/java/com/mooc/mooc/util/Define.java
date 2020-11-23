package com.mooc.mooc.util;


public class Define {

    public static final int PAGE_SIZE=6;

    //讨论记录页面size
    public static final int DISCUSSRECORD_PAGE_SIZE=10;

    public static final int DISCUSSION_PAGE_SIZE=20;

    public static final int PRE_PAGE_SIZE=8;

    //课程尚未开始
    public static final int COURSE_STATE_WAIT=0;
    //课程进行中
    public static final int COURSE_STATE_OPEN=1;
    //课程已结束
    public static final int COURSE_STATE_CLOSE=2;
    //课程审核状态为未通过
    public static final int CHECK_STATE_NOT_PASS=0;
    //课程审核状态为通过
    public static final int CHECK_STATE_PASSED=1;
    //所有人可选
    public static final int COURSE_AUTHORITY_PUBLIC=0;
    //仅本校学生可选
    public static final int COURSE_AUTHORITY_PRIVATE=1;
    public static final String MESSAGE_TYPE_SYS = "系统通知";

    public static final String MESSAGE_TYPE_ONLINE = "在线通知";

    public static final String MESSAGE_TYPE_NORMAL = "普通通知";

    public static final String MESSAGE_CODE_SYS = "sys";

    public static final String MESSAGE_CODE_NORMAL = "normal";

    public static final String MESSAGE_CODE_ONLINE = "online";

    public static final String MESSAGE_TYPE_OFFLINE = "offline";
}
