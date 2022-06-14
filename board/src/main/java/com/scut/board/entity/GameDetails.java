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
public class GameDetails {
    @TableId(type = IdType.AUTO)
    Long id; //游戏id
    String name; //游戏名称
    String cover; //封面地址
    String logo; //logo地址
    Long issuedTime; //游戏上架时间
    String downloadLink; //下载链接
    String classify; //分类
    float score; //评分
    Long scoreCount; //评分数量
    String totalScore; //评分总和
    String description; //简要描述
}
