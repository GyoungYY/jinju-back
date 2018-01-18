package com.aguang.jinjuback.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 金句表（jinju）
 */
public class Jinju {

    /* 主键 */
    private Integer jinjuId;

    /* 类型 */
    @NotNull
    private Integer type;

    /* 内容 */
    @NotNull
    @Length(min = 10, max = 500, message = "字数必须在10~500之间")
    private String content;

    /* 点赞总数 */
    private Integer upVoteCount;

    /* 点踩总睡 */
    private Integer downVoteCount;

    /* 收藏总数 */
    private Integer collectCount;

    private Integer commentCount;

    /* 用户Id */
    private Integer userId;

    /* 创建时间 */
    private long createTime;

    /* 更新时间 */
    private long updateTime;

    /* 区分删除 1：删除 0：未删除 */
    private Integer isDelete;

    public Integer getJinjuId() {
        return jinjuId;
    }

    public void setJinjuId(Integer jinjuId) {
        this.jinjuId = jinjuId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
