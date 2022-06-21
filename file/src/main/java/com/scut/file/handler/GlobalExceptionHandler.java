package com.scut.file.handler;

import com.scut.common.response.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public SingleResponse<Boolean> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        log.error("缺少请求参数，{}", ex.getMessage());
        return new SingleResponse<Boolean>().error(null, -2, "缺少请求参数");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public SingleResponse<Boolean> handleException(Exception ex) {
        log.error("服务器内部错误: {}", ex.getMessage());
        return new SingleResponse<Boolean>().error(null, -1, "服务器内部错误: " + ex.getMessage());
    }
}