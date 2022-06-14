package com.scut.common.response;

import com.scut.common.constant.HttpConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

import static com.scut.common.constant.HttpConstant.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiResponse<T> extends BaseResponse {
    Collection<T> data;

    public MultiResponse<T> success(Collection<T> data) {
        this.code = SUCCESS;
        this.msg = "";
        this.data = data;
        return this;
    }

    public MultiResponse<T> argInvalid(Collection<T> data, String msg) {
        this.code = ARG_INVALID;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public MultiResponse<T> verifyInvalid(Collection<T> data, String msg) {
        this.code = VERIFY_INVALID;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public MultiResponse<T> unknown(Collection<T> data, String msg) {
        this.code = UNKNOWN;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public MultiResponse<T> error(Collection<T> data, int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }
}
