package com.scut.board.entity;

public class GameForBoardEntity {
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCover() {
        return cover;
    }

    public String getLogo() {
        return logo;
    }

    public String getClassify() {
        return classify;
    }

    public float getScore() {
        return score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public void setScore(float score) {
        this.score = score;
    }

    long id; //游戏id
    String name; //游戏名称
    String cover; //封面地址
    String logo; //logo地址
    String classify; //游戏分类
    float score; //评分

}
