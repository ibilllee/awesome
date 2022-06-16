package com.scut.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select(" SELECT * FROM user WHERE email = #{email} ")
    @ResultType(User.class)
    User selectAllByEmail(@Param("email") String email);

    @Select("SELECT email FROM user")
    @ResultType(String.class)
    List<String> selectAllEmail();

    @Select("SELECT username FROM user")
    @ResultType(String.class)
    List<String> selectAllUsername();

    @Select(" SELECT avatar, username FROM user WHERE id = #{id} ")
    Map<Object, String> selectAvatarAndUsernameById(@Param("id") long id);
}
