package com.aguang.jinjuback.services;

import com.aguang.jinjuback.dao.AreaInfoDao;
import com.aguang.jinjuback.model.AreaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地区信息Service
 * @author li.kai
 * @since 2018/01/03
 */
@Service
public class AreaInfoService {

    public final static Logger LOG = LoggerFactory.getLogger(AreaInfoService.class);

    @Autowired
    private AreaInfoDao areaInfoDao;

    /**
     * 根据地区编码查询其下级地区列表
     * @param parentCode
     * @return
     */
    public List<AreaInfo> listByParentCode(String parentCode) {
        return areaInfoDao.listByParentCode(parentCode);
    }
}
