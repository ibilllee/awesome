package com.scut.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumDto {
    private long id;
    private long gameId;
    private long favorCount;
    private long articleCount;
    private String cover;
    private String name;
}
