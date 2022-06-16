package com.scut.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomentDto {
    private long id;
    private long userId;
    private String content;
    private long likeCount;
    private long createTime;
    private String attachment;
    private String avatar;
    private String username;
}
