package com.scut.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomentCommentDto {
    private long id;
    private String userId;
    private String momentId;
    private String content;
    private long replyId;
    private long replyCount;
    private long createTime;
}
