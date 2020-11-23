package com.mooc.mooc.vo;

import lombok.Data;

import java.util.List;

/**
 * View-Object: 视图层，用于展示层，它的作用是把某个指定页面（或组件）的所有数据封装起来
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private List<T> data;

    private T Object;

    public ResultVO(){

    }

    public ResultVO(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getObject() {
        return Object;
    }

    public void setObject(T object) {
        Object = object;
    }
}
