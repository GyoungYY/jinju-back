package com.aguang.jinjuback.services;

import com.aguang.jinjuback.dao.MeiwenDao;
import com.aguang.jinjuback.model.Meiwen;
import com.aguang.jinjuback.pojo.MeiwenInfo;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.pojo.constants.IsDeleteConstant;
import com.aguang.jinjuback.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MeiwenService {

    public final static Logger logger = LoggerFactory.getLogger(MeiwenService.class);

    @Autowired
    MeiwenDao meiwenDao;

    /**
     * 创建金句
     * @param meiwen
     * @param userId
     * @return
     */
    public Result createMeiwen(Meiwen meiwen, Integer userId) {
        Result result = new Result();
        try {
            Long currentTime = DateUtils.getCurrentTime();

            meiwen.setUserId(userId);
            meiwen.setBrowseCount(0);
            meiwen.setCollectCount(0);
            meiwen.setCommentCount(0);
            meiwen.setCreateTime(currentTime);
            meiwen.setUpdateTime(currentTime);
            meiwen.setIsDelete(IsDeleteConstant.UN_DELETE);

            meiwenDao.createMeiwen(meiwen);
            result.setSuccess(null, "创建成功");
        } catch (Exception e) {
            result.setError(null, "创建失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取美文列表
     * @param pageIndex
     * @param pageSize
     * @param userId
     * @return
     */
    public Result getMeiwenList(int pageIndex, int pageSize, Integer userId) {
        Integer m = (pageIndex - 1) * pageSize;
        ArrayList<MeiwenInfo> list = null;

        // 当前有登录用户，需要取出该用户是否有收藏等信息
        if(userId != null) {
            list = meiwenDao.listByPageWithUserId(m, pageSize, userId);
        }
        // 当前没有登录用户，则直接取出金句列表
        else {
            list = meiwenDao.listByPageWithoutUserId(m, pageSize);
        }

        Integer total = meiwenDao.getListCount();

        PageInfo<MeiwenInfo> pageInfo = new PageInfo(total, list);

        Result result = new Result();
        result.setSuccess(pageInfo, "获取美文列表数据成功");
        return result;
    }
}
