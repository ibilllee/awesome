package com.scut.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.scut.common.constant.HttpConstant.OK;
import static com.scut.common.constant.HttpConstant.UNPROCESSABLE;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleResponse<T> extends BaseResponse{
    T data;

    public SingleResponse<T> ok(T data) {
        this.code = OK;
        this.msg = "";
        this.data = data;
        return this;
    }

    public SingleResponse<T> unprocessable(T data, String msg) {
        this.code = UNPROCESSABLE;
        this.msg = msg;
        this.data = data;
        return this;
    }
}
