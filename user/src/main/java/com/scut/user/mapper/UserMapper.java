package com.scut.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select(" SELECT * FROM user WHERE email = #{email} ")
    User selectAllByEmail(String email);
}
