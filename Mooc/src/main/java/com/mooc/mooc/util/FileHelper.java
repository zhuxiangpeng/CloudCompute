package com.mooc.mooc.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileHelper {

    public static String upload(String directoryPath, MultipartFile file){
        //原文件名
        String originalName = file.getOriginalFilename();
        String newName;
        if(originalName!=null && originalName.lastIndexOf(".")!=-1){
            //取扩展名
            String ext = originalName.substring(originalName.lastIndexOf("."));
            //构造新文件名
            newName = UUID.randomUUID() + ext;
        }else
            newName = UUID.randomUUID().toString();

        //复制到映射的地址
        FileOutputStream fileOutputStream = null;
        try {
            //目标地址构造成输出流
            fileOutputStream = new FileOutputStream(directoryPath + newName);
            //复制文件
            FileCopyUtils.copy(file.getInputStream(),fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newName;
    }

    public static void delete(String directoryPath, String fileName){
        File file = new File(directoryPath+fileName);
        if(file.exists())
            file.delete();
    }
}
