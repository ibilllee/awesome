package com.scut.common.response;

import com.scut.common.constant.HttpConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

import static com.scut.common.constant.HttpConstant.OK;
import static com.scut.common.constant.HttpConstant.UNPROCESSABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> extends BaseResponse {
    Collection<T> data;
    Integer page;

    public PageResponse<T> ok(Collection<T> data, Integer page) {
        this.code = HttpConstant.OK;
        this.msg = "";
        this.data = data;
        this.page = page;
        return this;
    }

    public PageResponse<T> unprocessable(Collection<T> data, Integer page, String msg) {
        this.code = UNPROCESSABLE;
        this.msg = msg;
        this.data = data;
        this.page = page;
        return this;
    }
}
