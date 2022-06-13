package com.scut.common.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "排行榜信息")
public class GameForBoardDto {
    int id; //游戏id
    String name; //游戏名称
    String cover; //封面地址
    String logo; //logo地址
    String classify; //游戏分类
    float score; //评分
}
