package com.scut.common.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "游戏信息")
public class GameDetailsDto {
    long id; //游戏id
    String name; //游戏名称
    String cover; //封面地址
    String logo; //logo地址
    long issuedTime; //游戏上架时间
    String downloadLink; //下载链接
    String classify; //分类
    float score; //评分
    long scoreCount; //评分数量
    long totalScore; //评分总和
    String description; //简要描述
}
