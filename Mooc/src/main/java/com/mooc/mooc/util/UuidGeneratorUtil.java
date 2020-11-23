package com.mooc.mooc.util;

import java.util.UUID;

/**
 * @author xiangpeng.zhu
 * 生成主键uuid工具类
 */
public class UuidGeneratorUtil {

    public static void main(String[] args){
        //生成数量
        int num = 20;
        for(int i=0;i<num;i++){
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }
}
