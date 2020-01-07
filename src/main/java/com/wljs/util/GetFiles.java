package com.wljs.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetFiles {
    /**
     * 获取一个文件夹下的所有文件全路径
     *
     * @param path
     */
    public List<String> getAllFileName(String path) {
        List<String> fileList = new ArrayList<>();
        File file = new File(path);
        String[] names = file.list();
        if (names != null) {
            String[] completNames = new String[names.length];
            for (int i = 0; i < names.length; i++) {
                completNames[i] = path + names[i];
            }

            fileList.addAll(Arrays.asList(completNames));
        }

        List<String> resultlist = new ArrayList<>();
        for (String str : fileList) {
            if (str.contains(".xml")) {
                resultlist.add(str);
            }
        }

        return resultlist;
    }

}
