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
    //    @Value("${project.my-address}")
//    private String myAddress;
    @Value("${project.file-destination}")
    private String fileDestination;
    @Value("${project.file-dir}")
    private String fileDir;

    public FileDto uploadFile(MultipartFile avatar, long userId) {
        File file = null;
        try {
            file = FileUtils.multipartFileToFile(fileDestination + "/" + userId + "/",
                    UUID.randomUUID() + avatar.getName(), avatar);
        } catch (Exception e) {
            return null;
        }
        return new FileDto(file.getName(), fileDir + "/" + userId + "/" + file.getName());
    }

    public int removeFile(String fileName, long userId) {
        String path = fileDestination + "/" + userId + "/" + fileName;
        return FileUtils.deleteFile(path);
    }
}
