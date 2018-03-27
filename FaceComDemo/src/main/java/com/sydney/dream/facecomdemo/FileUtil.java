package com.sydney.dream.facecomdemo;

import java.io.File;

public class FileUtil {
    public static File[] getFileList(String path) {
        File file = new File(path);
        if (!file.exists() || file.isFile()) {
            System.out.println("所给路径不存在，或者所给路径是个文件。");
            return  null;
        }
        return file.listFiles();
    }
}
