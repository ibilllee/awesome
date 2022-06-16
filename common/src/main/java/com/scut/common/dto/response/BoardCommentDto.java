package com.scut.common.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "游戏评论")
public class BoardCommentDto {
    long id; //评论id
    long gameId; //游戏id
    long userId; //用户id
    String username; //用户名
    String avatar; //头像
    String content; //评论内容
    long score; //打分
    long createTime; //评论创建时间
}
