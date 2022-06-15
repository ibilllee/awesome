package com.scut.file.controller;

import com.scut.common.dto.request.DeleteFileParam;
import com.scut.common.dto.request.UploadFileParam;
import com.scut.common.dto.response.FileDto;
import com.scut.common.response.MultiResponse;
import com.scut.common.response.SingleResponse;
import com.scut.file.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.scut.common.constant.HttpConstant.USER_ID_HEADER;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/submit")
    public SingleResponse<FileDto> uploadFile(UploadFileParam uploadFileParam,
                                              @RequestHeader(USER_ID_HEADER) long userId) {
        FileDto fileDto = fileService.uploadFile(uploadFileParam.getFile(), userId);
        if (fileDto == null)
            return new SingleResponse<FileDto>().unknown(null, "上传文件失败");
        return new SingleResponse<FileDto>().success(fileDto);
    }

    @DeleteMapping("/remove")
    public SingleResponse<Boolean> removeFile(@RequestBody DeleteFileParam deleteFileParam,
                                              @RequestHeader(USER_ID_HEADER) long userId) {
        int result = fileService.removeFile(deleteFileParam.getFileName(), userId);
        if (result == 0)
            return new SingleResponse<Boolean>().unknown(false, "未知错误，删除文件失败");
        else if (result == -1)
            return new SingleResponse<Boolean>().error(false, 5001, "删除文件不存在或不属于你");
        return new SingleResponse<Boolean>().success(true);
    }
}
