package com.scut.space.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scut.common.dto.request.MomentParam;
import com.scut.common.dto.response.MomentDto;
import com.scut.common.dto.response.UserAvatarAndUsernameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("moment")
public class Moment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String content;
    private Long likeCount;
    private Long createTime;
    private String attachment;

    public Moment(long userId, MomentParam momentParam) {
        this.userId = userId;
        this.content = momentParam.getContent();
        this.likeCount = 0L;
        this.createTime = System.currentTimeMillis();
        this.attachment = momentParam.getAttachment();
    }

    public MomentDto getDto(UserAvatarAndUsernameDto dto) {
        return new MomentDto(getId(),
                getUserId(),
                getContent(),
                getLikeCount(),
                getCreateTime(),
                getAttachment(),
                dto.getAvatar(),
                dto.getUsername());
    }


}
