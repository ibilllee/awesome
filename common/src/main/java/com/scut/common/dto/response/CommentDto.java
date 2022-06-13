package com.scut.common.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "游戏评论")
public class CommentDto {
    int game_id; //游戏id
    int user_id; //用户id
    String username; //用户名
    String content; //评论内容
    int score; //打分
    int create_time; //评论创建时间
}
