package com.aguang.jinjuback.dao;

import com.aguang.jinjuback.model.Meiwen;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MeiwenDao {

    // 创建美文
    void createMeiwen(Meiwen meiwen);

}
