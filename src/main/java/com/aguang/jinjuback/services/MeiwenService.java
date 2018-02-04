package com.aguang.jinjuback.services;

import com.aguang.jinjuback.configuration.CustomException;
import com.aguang.jinjuback.dao.MeiwenDao;
import com.aguang.jinjuback.model.Meiwen;
import com.aguang.jinjuback.model.MeiwenComment;
import com.aguang.jinjuback.pojo.MeiwenCommentInfo;
import com.aguang.jinjuback.pojo.MeiwenInfo;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.pojo.constants.IsDeleteConstant;
import com.aguang.jinjuback.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;

@Service
public class MeiwenService {

    public final static Logger logger = LoggerFactory.getLogger(MeiwenService.class);

    @Autowired
    MeiwenDao meiwenDao;

    @Value("${qiniu.meiwenDomainName}")
    private String meiwenDomainName;

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

            meiwen.setCoverImgUrl(meiwenDomainName + meiwen.getCoverImgUrl());

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

    /**
     * 获取美文详情
     * @param id
     * @param userId
     * @return
     */
    public Result getMeiwen(Integer id, Integer userId) {
        Result result = new Result();

        try {
            MeiwenInfo meiwen = meiwenDao.getMeiwen(id, userId);

            // 阅读量+1
            meiwenDao.increaseBrowse(id);

            result.setSuccess(meiwen, "获取成功");
        } catch (Exception e) {
            result.setError("获取失败!");
        }

        return result;
    }

    /**
     * 创建评论
     * @param comment
     * @return
     */
    @Transactional
    public Result createComment(MeiwenComment comment, Integer userId) {

        Result result = new Result();
        try {

            comment.setUserId(userId);
            comment.setCreateTime(DateUtils.getCurrentTime());

            // 如果ParentId为空，则代表的是一级评论，将ParentId设置为0
//            if(comment.getParentId()==null || comment.getParentId().equals(0)) {
//                comment.setParentId(0);

                // 金句的评论总数加1
                Integer affectCount = meiwenDao.increaseMeiwenComment(comment.getMeiwenId());
                if(affectCount == 0) {
                    throw new CustomException("美文不存在，不可评论!");
                }

//            } else {
//
//                // 一级评论的评论总数加1
//                Integer affectCount = jinjuDao.increaseComment(comment.getParentId());
//
//                if(affectCount == 0) {
//                    throw new CustomException("一级评论不存在，不可评论!");
//                }
//
//            }

            meiwenDao.createMeiwenComment(comment);

            result.setSuccess("操作成功!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setError("操作失败");
            if(e instanceof CustomException) {
                result.setError(e.getMessage());
            }
            logger.error("操作失败!", e);
        }
        return result;
    }

    /**
     * 获取评论列表
     * @param meiwenId
     * @param parentId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result listComment(Integer meiwenId, Integer parentId, int pageIndex, int pageSize) {

        Result result = new Result();

        try {
            if(parentId == null) {
                parentId = 0;
            }

            Integer m = (pageIndex - 1) * pageSize;
            ArrayList<MeiwenCommentInfo> list = meiwenDao.listMeiwenCommentByPage(m, pageSize, meiwenId, parentId);

            Integer total = meiwenDao.listCommentCount(meiwenId, parentId);

            PageInfo<MeiwenCommentInfo> pageInfo = new PageInfo(total, list);

            result.setSuccess(pageInfo, "评论列表数据获取成功");
        } catch (Exception e) {
            result.setError("评论列表数据获取失败");
            logger.error("操作失败!", e);
        }

        return result;
    }
}
