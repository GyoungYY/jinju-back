package com.aguang.jinjuback.dao;

import com.aguang.jinjuback.model.AreaInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * 地区信息Dao
 * @author li.kai
 * @since 2018/01/03
 */
@Mapper
@Repository
public interface AreaInfoDao {

    @Select("SELECT * from sys_area_info WHERE parent_code=#{parentCode}")
    ArrayList<AreaInfo> listByParentCode(@Param("parentCode") String parentCode);
}
