package com.aguang.jinjuback.services.admin;

import com.aguang.jinjuback.configuration.CustomException;
import com.aguang.jinjuback.dao.admin.AdJinjuDao;
import com.aguang.jinjuback.model.Jinju;
import com.aguang.jinjuback.pojo.admin.AdJinjuInfo;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.pojo.constants.JinjuTypeConstant;
import com.aguang.jinjuback.services.AreaInfoService;
import com.aguang.jinjuback.services.JinjuService;
import com.aguang.jinjuback.utils.PropertieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class AdJinjuService {

    public final static Logger LOG = LoggerFactory.getLogger(AreaInfoService.class);

    @Autowired
    private AdJinjuDao jinjuDao;

    @Autowired
    private JinjuService jinjuService;

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageInfo<AdJinjuInfo> getJinjuList(int pageIndex, int pageSize) {
        Integer m = (pageIndex - 1) * pageSize;

        Integer total = jinjuDao.getListCount();

        ArrayList<AdJinjuInfo> list = jinjuDao.listByPage(m, pageSize);

        PageInfo<AdJinjuInfo> pageInfo = new PageInfo(total, list);

        for (AdJinjuInfo jinjuInfo : list) {
            // 值域处理
            PropertieUtils.conversionCode(jinjuInfo);
        }

        return pageInfo;
    }

    /**
     * 审核后台金句
     * @param id
     */
    @Transactional
    public void auditJinju(Integer id, Integer type) {

        AdJinjuInfo adJinju = jinjuDao.getAdJinju(id);

        if(adJinju==null) {
            throw new CustomException("id不存在!");
        }

        // 审核后台金句
        jinjuDao.auditAdJinju(id);

        // 创建前台金句
        Jinju jinju = new Jinju();
        jinju.setContent(adJinju.getContent());
        jinju.setType(type);

        Integer userId = null;
        if(JinjuTypeConstant.FUN.equals(type)) {
            // 金段子
            userId = 342;
        } else if(JinjuTypeConstant.EMOTION.equals(type)) {
            // 情感头条
            userId = 351;
        } else if(JinjuTypeConstant.HOT.equals(type)) {
            // Gyoung阿广
            userId = 333;
        } else {
            throw new CustomException("不支持的金句类型!");
        }

        jinjuService.createJinju(jinju, userId);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id) {

        Integer affectRows  = jinjuDao.delete(id);

        if(affectRows!=null && affectRows==0) {
            throw new CustomException("id不存在!");
        }
    }
}
