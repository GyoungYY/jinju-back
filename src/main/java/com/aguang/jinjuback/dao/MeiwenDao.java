package com.aguang.jinjuback.dao;

import com.aguang.jinjuback.model.Meiwen;
import com.aguang.jinjuback.pojo.MeiwenInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface MeiwenDao {

    /**
     * 创建美文
     * @param meiwen
     */
    void createMeiwen(Meiwen meiwen);

    /**
     * 获取美文的列表信息（用户已登录的情况）
     * @return
     */
    ArrayList<MeiwenInfo> listByPageWithUserId(@Param("m") int m, @Param("n") int n, @Param("userId") int userId);

    /**
     *  获取美文的列表信息（用户未登录的情况）
     * @return
     */
    ArrayList<MeiwenInfo> listByPageWithoutUserId(@Param("m") int m, @Param("n") int n);

    @Select("SELECT COUNT(1) FROM meiwen")
    int getListCount();
}
