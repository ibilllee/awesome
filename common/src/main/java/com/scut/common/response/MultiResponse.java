package com.scut.common.response;

import com.scut.common.constant.HttpConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiResponse<T> extends BaseResponse{
    Collection<T> data;

    public MultiResponse<T> ok(Collection<T> data) {
        this.code = HttpConstant.OK;
        this.msg = "";
        this.data = data;
        return this;
    }

    public MultiResponse<T> unprocessable(Collection<T> data, String msg) {
        this.code = HttpConstant.UNPROCESSABLE;
        this.msg = msg;
        this.data = data;
        return this;
    }
}
