package com.aguang.jinjuback.pojo;

import java.io.Serializable;

/**
 * 返回页面的评论Info
 */
public class JinjuCommentInfo implements Serializable {

    // 金句类型
    private Integer id;

    // 金句id
    private Integer jinjuId;

    // 一级评论id
    private Integer parentId;

    // 金句内容
    private String content;

    // 点赞总数
    private Integer upVoteCount;

    // 点踩总数
    private Integer downVoteCount;

    // 评论总数
    private Integer commentCount;

    // 金句创建用户id
    private Integer userId;

    // 金句创建用户名
    private String username;

    /* 头像 */
    private String photoUrl;

    // 评论创建时间
    private long createTime;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
