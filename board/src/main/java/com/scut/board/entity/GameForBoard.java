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
public class GameForBoard {
    public Long getId() {
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

    @TableId(type = IdType.AUTO)
    Long id; //游戏id
    String name; //游戏名称
    String cover; //封面地址
    String logo; //logo地址
    String classify; //游戏分类
    float score; //评分

}
