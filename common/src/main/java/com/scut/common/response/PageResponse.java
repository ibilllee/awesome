package com.scut.common.response;

import com.scut.common.constant.HttpConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

import static com.scut.common.constant.HttpConstant.*;
import static com.scut.common.constant.HttpConstant.UNKNOWN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> extends BaseResponse {
    Collection<T> data;
    int page;

    public PageResponse<T> success(Collection<T> data, int page) {
        this.code = SUCCESS;
        this.page = page;
        this.msg = "";
        this.data = data;
        return this;
    }

    public PageResponse<T> argInvalid(Collection<T> data, String msg) {
        this.code = ARG_INVALID;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public PageResponse<T> verifyInvalid(Collection<T> data, String msg) {
        this.code = VERIFY_INVALID;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public PageResponse<T> unknown(Collection<T> data, String msg) {
        this.code = UNKNOWN;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public PageResponse<T> error(Collection<T> data, int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }
}
