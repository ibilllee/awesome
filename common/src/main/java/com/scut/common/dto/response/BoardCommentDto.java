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
    int id; //评论id
    int gameId; //游戏id
    int userId; //用户id
    String username; //用户名
    String content; //评论内容
    int score; //打分
    int createTime; //评论创建时间
}
