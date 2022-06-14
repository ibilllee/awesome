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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(Long issuedTime) {
        this.issuedTime = issuedTime;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Long getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Long scoreCount) {
        this.scoreCount = scoreCount;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
