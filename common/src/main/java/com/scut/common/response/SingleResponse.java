package com.scut.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

import static com.scut.common.constant.HttpConstant.*;
import static com.scut.common.constant.HttpConstant.UNKNOWN;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleResponse<T> extends BaseResponse{
    T data;

    public SingleResponse<T> success(T data) {
        this.code = SUCCESS;
        this.msg = "";
        this.data = data;
        return this;
    }

    public SingleResponse<T> argInvalid(T data, String msg) {
        this.code = ARG_INVALID;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public SingleResponse<T> verifyInvalid(T data, String msg) {
        this.code = VERIFY_INVALID;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public SingleResponse<T> unknown(T data, String msg) {
        this.code = UNKNOWN;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public SingleResponse<T> error(T data, int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }
}
