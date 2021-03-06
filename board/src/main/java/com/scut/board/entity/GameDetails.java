package com.scut.board.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scut.common.dto.request.GameParam;
import com.scut.common.dto.response.GameDetailsDto;
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
    float score; //评分S
    Long scoreCount; //评分数量
    Long totalScore; //评分总和
    String description; //简要描述

    public GameDetails(GameParam gameParam) {
        this.name = gameParam.getName();
        this.cover = gameParam.getCover();
        this.logo = gameParam.getLogo();
        this.issuedTime = gameParam.getIssuedTime();
        this.downloadLink = gameParam.getDownloadLink();
        this.classify = gameParam.getClassify();
        this.score = 0.0f;
        this.scoreCount = 0L;
        this.totalScore = 0L;
        this.description = gameParam.getDescription();
    }

    public GameDetailsDto getDto() {
        return new GameDetailsDto(this.id,
                this.name,
                this.cover,
                this.logo,
                this.issuedTime,
                this.downloadLink,
                this.classify,
                this.score,
                this.scoreCount,
                this.totalScore,
                this.description);
    }
}
