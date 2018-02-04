package com.aguang.jinjuback.services;

import com.aguang.jinjuback.configuration.CustomException;
import com.aguang.jinjuback.dao.JinjuDao;
import com.aguang.jinjuback.model.JinjuComment;
import com.aguang.jinjuback.model.Jinju;
import com.aguang.jinjuback.pojo.JinjuCommentInfo;
import com.aguang.jinjuback.pojo.JinjuInfo;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.pojo.constants.ConfirmOrCalcelConstant;
import com.aguang.jinjuback.pojo.constants.IsDeleteConstant;
import com.aguang.jinjuback.pojo.constants.VoteConstant;
import com.aguang.jinjuback.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;

@Service
public class JinjuService {

    public final static Logger LOG = LoggerFactory.getLogger(JinjuService.class);

    @Autowired
    JinjuDao jinjuDao;

    /**
     * 创建金句
     * @param jinju
     * @param userId
     * @return
     */
    public Result createJinju(Jinju jinju, Integer userId) {
        Result result = new Result();
        try {
            Long currentTime = DateUtils.getCurrentTime();

            jinju.setUserId(userId);
            jinju.setUpVoteCount(0);
            jinju.setDownVoteCount(0);
            jinju.setCollectCount(0);
            jinju.setCommentCount(0);
            jinju.setCreateTime(currentTime);
            jinju.setUpdateTime(currentTime);
            jinju.setIsDelete(IsDeleteConstant.UN_DELETE);

            jinjuDao.createJinju(jinju);
            result.setSuccess(null, "创建成功");
        } catch (Exception e) {
            result.setError(null, "创建失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取金句信息
     * @param id
     * @return
     */
    public JinjuInfo getJinju(Integer id, Integer userId) {

        JinjuInfo jinju = null;
        // 当前有登录用户，需要取出该用户是否有点赞、收藏等信息
        if(userId != null) {
            jinju = jinjuDao.getJinjuWithUserId(id, userId);
        }
        // 当前没有登录用户，则直接取出金句列表
        else {
            jinju = jinjuDao.getJinjuWithoutUserId(id);
        }
        return jinju;
    }

    /**
     * 获取金桔句列表
     * @param pageIndex
     * @param pageSize
     * @param userId
     * @return
     */
    public Result getJinjuList(int pageIndex, int pageSize, Integer userId) {
        Integer m = (pageIndex - 1) * pageSize;
        ArrayList<JinjuInfo> list = null;

        // 当前有登录用户，需要取出该用户是否有点赞、收藏等信息
        if(userId != null) {
            list = jinjuDao.listByPageWithUserId(m, pageSize, userId);
        }
        // 当前没有登录用户，则直接取出金句列表
        else {
            list = jinjuDao.listByPageWithoutUserId(m, pageSize);
        }

        Integer total = jinjuDao.getListCount();

        PageInfo<JinjuInfo> pageInfo = new PageInfo(total, list);

        Result result = new Result();
        result.setSuccess(pageInfo, "金句列表数据成功");
        return result;
    }

    /**
     * 点赞
     * @param jijuId    金句id
     * @param type      区分确认和取消
     * @param userId    用户id
     * @return
     */
    @Transactional
    public Result upVote(Integer jijuId, String type, Integer userId) {
        Result result = new Result();
        try {
            // 1:确认赞
            if(ConfirmOrCalcelConstant.CONFIRM.equals(type)) {

                // 判断当前是否存在踩，如果存在，则删除踩
                Integer hasVote = jinjuDao.hasVote(jijuId, userId, VoteConstant.DOWN);
                if(hasVote != null) {
                    jinjuDao.deleteVote(jijuId, userId, VoteConstant.DOWN);
                    jinjuDao.decreaseDownVote(jijuId);
                }

                // 创建赞
                jinjuDao.createVote(jijuId, userId, VoteConstant.UP, DateUtils.getCurrentTime());
                jinjuDao.increaseUpVote(jijuId);
            }
            // 2:取消赞
            else if(ConfirmOrCalcelConstant.CALCEL.equals(type)) {

                // 删除赞
                Integer affectCount = jinjuDao.deleteVote(jijuId, userId, VoteConstant.UP);
                if(affectCount == 0) {
                    throw new CustomException("当前没有点赞，不可取消!");
                }

                jinjuDao.decreaseUpVote(jijuId);
            }

            result.setSuccess("操作成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setError("操作失败");
            if(e instanceof CustomException) {
                result.setError(e.getMessage());
            }
            LOG.error("操作失败", e);
        }
        return result;
    }

    /**
     * 踩
     * @param jijuId    金句id
     * @param type      区分确认和取消
     * @param userId    用户id
     * @return
     */
    @Transactional
    public Result downVote(Integer jijuId, String type, Integer userId) {
        Result result = new Result();
        try {

            // 1:确认踩
            if(ConfirmOrCalcelConstant.CONFIRM.equals(type)) {

                // 判断当前是否存在赞，如果存在，则删除赞
                Integer hasVote = jinjuDao.hasVote(jijuId, userId, VoteConstant.UP);
                if(hasVote != null) {
                    jinjuDao.deleteVote(jijuId, userId, VoteConstant.UP);
                    jinjuDao.decreaseUpVote(jijuId);
                }

                // 创建踩
                jinjuDao.createVote(jijuId, userId, VoteConstant.DOWN, DateUtils.getCurrentTime());
                jinjuDao.increaseDownVote(jijuId);

            }
            // 2:取消踩
            else if(ConfirmOrCalcelConstant.CALCEL.equals(type)) {

                // 删除踩
                Integer affectCount = jinjuDao.deleteVote(jijuId, userId, VoteConstant.DOWN);
                if(affectCount == 0) {
                    throw new CustomException("当前没有点赞，不可取消!");
                }

                jinjuDao.decreaseDownVote(jijuId);
            }

            result.setSuccess("操作成功");

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setError("操作失败");
            if(e instanceof CustomException) {
                result.setError(e.getMessage());
            }
            LOG.error("点踩失败", e);
        }
        return result;
    }

    /**
     * 收藏
     * @param jijuId    金句id
     * @param type      区分确认和取消
     * @param userId    用户id
     * @return
     */
    @Transactional
    public Result collect(Integer jijuId, String type, Integer userId) {
        Result result = new Result();
        try {

            // 1:确认收藏
            if(ConfirmOrCalcelConstant.CONFIRM.equals(type)) {

                // 创建收藏
                jinjuDao.createCollect(jijuId, userId, 1,DateUtils.getCurrentTime());
                jinjuDao.increaseCollect(jijuId);
            }
            // 2:取消收藏
            else if(ConfirmOrCalcelConstant.CALCEL.equals(type)) {

                // 删除收藏
                Integer affectCount = jinjuDao.deleteCollect(jijuId, userId);
                if(affectCount == 0) {
                    throw new CustomException("当前没有收藏，不可取消!");
                }

                jinjuDao.decreaseCollect(jijuId);
            }

            result.setSuccess("操作成功!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setError("操作失败");
            if(e instanceof CustomException) {
                result.setError(e.getMessage());
            }
            LOG.error("操作失败!", e);
        }
        return result;
    }

    /**
     * 创建评论
     * @param comment
     * @return
     */
    @Transactional
    public Result createComment(JinjuComment comment, Integer userId) {

        Result result = new Result();
        try {

            comment.setUpVoteCount(0);
            comment.setDownVoteCount(0);
            comment.setCommentCount(0);
            comment.setUserId(userId);
            comment.setCreateTime(DateUtils.getCurrentTime());

            // 如果ParentId为空，则代表的是一级评论，将ParentId设置为0
            if(comment.getParentId()==null || comment.getParentId().equals(0)) {
                comment.setParentId(0);

                // 金句的评论总数加1
                Integer affectCount = jinjuDao.increaseJinjuComment(comment.getJinjuId());
                if(affectCount == 0) {
                    throw new CustomException("金句不存在，不可评论!");
                }

            } else {

                // 一级评论的评论总数加1
                Integer affectCount = jinjuDao.increaseComment(comment.getParentId());

                if(affectCount == 0) {
                    throw new CustomException("一级评论不存在，不可评论!");
                }

            }

            jinjuDao.createComment(comment);

            result.setSuccess("操作成功!");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setError("操作失败");
            if(e instanceof CustomException) {
                result.setError(e.getMessage());
            }
            LOG.error("操作失败!", e);
        }
        return result;
    }

    /**
     * 获取评论列表
     * @param jinjuId
     * @param parentId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result listComment(Integer jinjuId, Integer parentId, int pageIndex, int pageSize) {

        Result result = new Result();

        try {
            if(parentId == null) {
                parentId = 0;
            }

            Integer m = (pageIndex - 1) * pageSize;
            ArrayList<JinjuCommentInfo> list = jinjuDao.listCommentByPage(m, pageSize, jinjuId, parentId);

            Integer total = jinjuDao.listCommentCount(jinjuId, parentId);

            PageInfo<JinjuComment> pageInfo = new PageInfo(total, list);

            result.setSuccess(pageInfo, "评论列表数据获取成功");
        } catch (Exception e) {
            result.setError("评论列表数据获取失败");
            LOG.error("操作失败!", e);
        }

        return result;
    }

    /**
     * 评论点赞
     * @param commentId
     * @param type
     * @return
     */
    @Transactional
    public Result upVoteComment(Integer commentId, String type) {
        Result result = new Result();

        try {
            if(ConfirmOrCalcelConstant.CONFIRM.equals(type)) {

                // 一级评论点赞数加1
                Integer affectCount = jinjuDao.increaseCommentUpVote(commentId);
                if(affectCount == 0) {
                    throw new CustomException("一级评论不存在，不可点赞!");
                }

            } else if(ConfirmOrCalcelConstant.CALCEL.equals(type)) {

                // 一级评论点赞数减1
                Integer affectCount = jinjuDao.decreaseCommentUpVote(commentId);
                if(affectCount == 0) {
                    throw new CustomException("一级评论不存在，不可取消点赞!");
                }
            }

            result.setSuccess("操作成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setError("操作失败");
            if(e instanceof CustomException) {
                result.setError(e.getMessage());
            }
            LOG.error("操作失败!", e);
        }

        return result;
    }
}
