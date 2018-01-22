package com.aguang.jinjuback.services;

import com.aguang.jinjuback.dao.MeiwenDao;
import com.aguang.jinjuback.model.Meiwen;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.constants.IsDeleteConstant;
import com.aguang.jinjuback.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
