package com.aguang.jinjuback.dao;

import com.aguang.jinjuback.model.Jinju;
import com.aguang.jinjuback.model.JinjuComment;
import com.aguang.jinjuback.pojo.JinjuCommentInfo;
import com.aguang.jinjuback.pojo.JinjuInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface JinjuDao {

    void createJinju(Jinju jinju);

    /**
     * 获取金句的列表信息（用户已登录的情况）
     * @return
     */
    ArrayList<JinjuInfo> listByPageWithUserId(@Param("m") int m, @Param("n") int n, @Param("userId") int userId);

    /**
     *  获取金句的列表信息（用户未登录的情况）
     * @return
     */
    ArrayList<JinjuInfo> listByPageWithoutUserId(@Param("m") int m, @Param("n") int n);

    JinjuInfo getJinjuWithUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    JinjuInfo getJinjuWithoutUserId(@Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM jinju")
    int getListCount();

    /**
     * 创建点赞、踩
     */
    @Insert("INSERT INTO `jj_vote`(jinju_id, user_id, type, create_time) VALUES (#{jijuId}, #{userId}, #{type}, #{currentTime})")
    Integer createVote(@Param("jijuId") Integer jijuId,
                    @Param("userId") Integer userId,
                    @Param("type") Integer type,
                    @Param("currentTime") Long currentTime);

    /**
     * 删除点赞、踩
     */
    @Delete("delete from `jj_vote` where jinju_id=#{jijuId} and user_id=#{userId} and type=#{type}")
    Integer deleteVote(@Param("jijuId") Integer jijuId, @Param("userId") Integer userId, @Param("type") Integer type);

    @Select("select 1 from `jj_vote` where jinju_id=#{jijuId} and user_id=#{userId} and type=#{type}")
    Integer hasVote(@Param("jijuId") Integer jijuId,
                    @Param("userId") Integer userId,
                    @Param("type") Integer type);

    /**
     * 点赞数加1
     * @param jijuId
     */
    @Update("update `jinju` set up_vote_count=up_vote_count+1 where jinju_id=#{jijuId}")
    void increaseUpVote(Integer jijuId);

    /**
     * 点赞数减1
     * @param jijuId
     */
    @Update("update `jinju` set up_vote_count=up_vote_count-1 where jinju_id=#{jijuId}")
    void decreaseUpVote(Integer jijuId);

    /**
     * 点踩数加1
     * @param jijuId
     */
    @Update("update `jinju` set down_vote_count=down_vote_count+1 where jinju_id=#{jijuId}")
    void increaseDownVote(Integer jijuId);

    /**
     * 点踩数减1
     * @param jijuId
     */
    @Update("update `jinju` set down_vote_count=down_vote_count-1 where jinju_id=#{jijuId}")
    void decreaseDownVote(Integer jijuId);

    /**
     * 收藏
     */
    @Insert("INSERT INTO `jj_collect`(jinju_id, user_id, collect, create_time) VALUES (#{jijuId}, #{userId}, #{collect}, #{currentTime})")
    void createCollect(@Param("jijuId") Integer jijuId,
                       @Param("userId") Integer userId,
                       @Param("collect") Integer collect,
                       @Param("currentTime") Long currentTime);

    /**
     * 删除收藏
     */
    @Delete("delete from `jj_collect` where jinju_id=#{jijuId} and user_id=#{userId} ")
    Integer deleteCollect(@Param("jijuId") Integer jijuId, @Param("userId") Integer userId);

    /**
     * 收藏总数+1
     * @param jijuId
     */
    @Update("update `jinju` set collect_count=collect_count+1 where jinju_id=#{jijuId}")
    void increaseCollect(Integer jijuId);

    /**
     * 收藏总数+1
     * @param jijuId
     */
    @Update("update `jinju` set collect_count=collect_count-1 where jinju_id=#{jijuId}")
    void decreaseCollect(Integer jijuId);

    /**
     * 创建评论
     * @param comment
     */
    void createComment(JinjuComment comment);

    /**
     * 金句的评论总数加1
     * @param jinjuId
     */
    @Update("update `jinju` set comment_count=comment_count+1 where jinju_id=#{jijuId}")
    Integer increaseJinjuComment(Integer jinjuId);

    /**
     * 一级评论的评论总数加1
     * @param parentId
     */
    @Update("update `jj_comment` set comment_count=comment_count+1 where id=#{parentId}")
    Integer increaseComment(Integer parentId);

    /**
     * 获取评论列表
     * @return
     */
    ArrayList<JinjuCommentInfo> listCommentByPage(@Param("m") int m, @Param("n") int n, @Param("jinjuId") Integer jinjuId, @Param("parentId") Integer parentId);

    /**
     * 获取评论总数
     * @param jinjuId
     * @param parentId
     * @return
     */
    @Select("SELECT COUNT(1) FROM jj_comment c where c.jinju_id = #{jinjuId} and c.parent_id = #{parentId}")
    Integer listCommentCount(@Param("jinjuId") Integer jinjuId, @Param("parentId") Integer parentId);

    /**
     * 一级评论点赞数加1
     * @param id
     */
    @Update("update `jj_comment` set up_vote_count=up_vote_count+1 where id=#{id}")
    Integer increaseCommentUpVote(Integer id);

    /**
     * 一级评论点赞数减1
     * @param id
     */
    @Update("update `jj_comment` set up_vote_count=up_vote_count-1 where id=#{id}")
    Integer decreaseCommentUpVote(Integer id);
}
