package com.scut.board.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("game_comment")
public class GameComment {
    @TableId(type = IdType.AUTO)
    Long id;
    Long userId;
    Long gameId;
    String content;
    Long gameScore;
    Long createTime;
}
