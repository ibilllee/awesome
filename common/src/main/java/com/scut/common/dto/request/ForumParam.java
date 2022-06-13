package com.scut.common.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumParam {
    @ApiModelProperty(value = "对应游戏ID", required = true)
    private String gameId;
}
