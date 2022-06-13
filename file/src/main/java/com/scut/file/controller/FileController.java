package com.scut.file.controller;

import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/submit")
    public MultiResponse<String> uploadFile(MultipartFile file) {
        return null;
    }

    @DeleteMapping("/remove")
    public SingleResponse<Integer> removeFile(String fileURI) {
        return null;
    }
}
