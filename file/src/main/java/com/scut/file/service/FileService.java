package com.scut.file.service;

import com.scut.common.dto.response.FileDto;
import com.scut.file.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {
    @Value("${project.my-address}")
    private String myAddress;

    public FileDto uploadFile(MultipartFile avatar, long userId) {
        File file = null;
        try {
            file = FileUtils.multipartFileToFile("static/file/" + userId + "/",
                    UUID.randomUUID() + avatar.getName(), avatar);
        } catch (Exception e) {
            return null;
        }
        return new FileDto(file.getName(), myAddress + "file/" + userId + "/" + file.getName());
    }

    public int removeFile(String fileName, long userId) {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()
                + "static/file/" + userId + "/" + fileName;
        return FileUtils.deleteFile(path);
    }
}
