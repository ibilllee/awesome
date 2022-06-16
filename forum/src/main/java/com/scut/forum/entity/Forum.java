package com.scut.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("forum")
public class Forum {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long gameId;
    private Long favorCount;
    private Long articleCount;
}
