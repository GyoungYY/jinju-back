package com.aguang.jinjuback.dao;

import com.aguang.jinjuback.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    @Insert("INSERT INTO jinju_db.jj_user(username,password,create_time,update_time) VALUES(#{username},#{password},#{curTime},#{curTime})")
    void createUser(@Param("username") String username,@Param("password") String password,@Param("curTime") Long curTime);

    @Select("SELECT * FROM jinju_db.jj_user WHERE user_id = #{id}")
    User getUser(@Param("id") int id);

    void updateUser(User user);

    @Select("select * from jj_user where username=#{username}")
    User getUserByUsername(String username);

    @Update("update jinju_db.jj_user set last_login_time=#{curTime} where user_id=#{id}")
    void updateLastLoginTime(@Param("id") Integer id, @Param("curTime") Long curTime);

    @Select("select * from jj_user where user_id=#{userId}")
    User getUserById(Integer userId);

    /**
     * 更新用户头像
     * @param id
     * @param photoUrl
     */
    @Update("update jinju_db.jj_user set photo_url=#{photoUrl} where user_id=#{id}")
    void updatePhotoUrl(@Param("id") Integer id, @Param("photoUrl") String photoUrl);
}
