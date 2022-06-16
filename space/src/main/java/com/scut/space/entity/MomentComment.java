package com.scut.space.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scut.common.dto.request.MomentCommentParam;
import com.scut.common.dto.response.MomentCommentDto;
import com.scut.common.dto.response.UserAvatarAndUsernameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("moment_comment")
public class MomentComment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long momentId;
    private String content;
    private Long replyId;
    private Long replyCount;
    private Long createTime;

    public MomentComment(long userId, MomentCommentParam momentCommentParam) {
        this.userId = userId;
        this.momentId = momentCommentParam.getMomentId();
        this.content = momentCommentParam.getContent();
        this.replyId = momentCommentParam.getReplyId();
        this.replyCount = 0L;
        this.createTime = System.currentTimeMillis();
    }

    public MomentCommentDto getDto(UserAvatarAndUsernameDto dto) {
        return new MomentCommentDto(this.id,
                this.userId,
                this.momentId,
                this.content,
                this.replyId,
                this.replyCount,
                this.createTime,
                dto.getAvatar(),
                dto.getUsername());
    }
}
