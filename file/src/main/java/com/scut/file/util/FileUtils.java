package com.scut.file.util;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtils {
    public static File multipartFileToFile(String path, String userId, MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
//            String dirPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + path;
            String dirPath =  path;
            String filePath = dirPath + userId + getExtName(file.getOriginalFilename(), '.');

            new File(dirPath).mkdirs();
            toFile = new File(filePath);

            System.out.println(toFile.getAbsolutePath());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除本地临时文件
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    public static int deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) return -1;
        return file.delete() ? 1 : 0;
    }

    public static void writeLocalStrOne(String str, String path) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        if (str != null && !"".equals(str)) {
            FileWriter fw = new FileWriter(file, true);
            fw.write(str);//写入本地文件中
            fw.flush();
            fw.close();
            // System.out.println("执行完毕!");
        }
    }

    private static String getExtName(String s, char split) {
        int i = s.lastIndexOf(split);
        int leg = s.length();
        return (i > 0 ? (i + 1) == leg ? " " : s.substring(i, s.length()) : " ");
    }


    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }

}




