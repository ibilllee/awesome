package com.scut.common.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "游戏列表")
public class SearchGameListDto
{
    long id; //游戏id
    long score; // 游戏评分
    String name; //游戏名称
    String classify; //游戏分类
}
