package com.aguang.jinjuback.dao;

import com.aguang.jinjuback.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoDao {



    @Select("SELECT * FROM jinju_db.jj_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);


}
