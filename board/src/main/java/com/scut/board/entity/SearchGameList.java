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
@TableName("game")
public class SearchGameList {
    @TableId(type = IdType.AUTO)
    long id; //游戏id
    long score; // 游戏评分
    String name; //游戏名称
    String classify; //游戏分类
}
