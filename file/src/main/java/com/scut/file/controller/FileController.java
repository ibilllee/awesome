package com.scut.file.controller;

import com.scut.common.dto.request.DeleteFileParam;
import com.scut.common.dto.request.UploadFileParam;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/submit")
    public MultiResponse<String> uploadFile(@RequestBody UploadFileParam uploadFileParam) {
        return null;
    }

    @DeleteMapping("/remove")
    public SingleResponse<Integer> removeFile(@RequestBody DeleteFileParam deleteFileParam) {
        return null;
    }
}
