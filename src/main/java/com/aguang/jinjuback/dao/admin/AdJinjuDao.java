package com.aguang.jinjuback.dao.admin;

import com.aguang.jinjuback.pojo.admin.AdJinjuInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface AdJinjuDao {

    @Insert("insert into ad_jinju(content, source, create_time) values(#{content}, #{source}, #{createTime})")
    void createAdJinju(@Param("content") String content, @Param("source") Integer source, @Param("createTime") Long createTime);

    @Select("select count(1) from ad_jinju where content = #{content} ")
    Integer getAdJinjuByContent(String content);

    /**
     * 获取列表信息
     * @return
     */
    @Select("select * from ad_jinju t where t.is_delete=0 order by t.audit_status, t.id desc limit #{m},#{n}")
    ArrayList<AdJinjuInfo> listByPage(@Param("m") int m, @Param("n") int n);

    @Select("select * from ad_jinju where id = #{id} ")
    AdJinjuInfo getAdJinju(Integer id);

    @Update("update ad_jinju set audit_status=1 where id = #{id} ")
    Integer auditAdJinju(Integer id);
}