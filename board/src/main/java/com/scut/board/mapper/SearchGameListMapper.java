package com.scut.board.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.board.entity.SearchGameList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchGameListMapper extends BaseMapper<SearchGameList> {
    @Select("SELECT id, score ,name, classify From game where name Like #{KeyWord}")
    @ResultType(SearchGameList.class)
    List<SearchGameList> selectSearchGameList(@Param("KeyWord") String KeyWord);
}
