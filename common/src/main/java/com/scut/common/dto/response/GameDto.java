package com.scut.common.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "游戏信息")
public class GameDto {
    int id; //游戏id
    String name; //游戏名称
    String cover; //封面地址
    String logo; //logo地址
    int issued_time; //游戏上架时间
    String download_link; //下载链接
    String classify; //分类
    float score; //评分
    int score_count; //评分数量
    String total_score; //评分总和
    String description; //简要描述
}
