package com.aguang.jinjuback.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 评论表
 */
public class Comment {

    /* 主键 */
    private Integer id;

    /* 金句Id */
    @NotNull
    private Integer jinjuId;

    /* 用户Id */
    private Integer userId;

    /* 内容 */
    @NotNull(message = "评论内容不能为空")
    @Length(min = 1, max = 500, message = "字数必须在1~500之间")
    private String content;

    /* 点赞总数 */
    private Integer upVoteCount;

    /* 点踩总数 */
    private Integer downVoteCount;

    /* 二级评论总数 */
    private Integer commentCount;

    /* 一级评论Id */
    private Integer parentId;

    /* 创建时间 */
    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJinjuId() {
        return jinjuId;
    }

    public void setJinjuId(Integer jinjuId) {
        this.jinjuId = jinjuId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(Integer upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public Integer getDownVoteCount() {
        return downVoteCount;
    }

    public void setDownVoteCount(Integer downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
