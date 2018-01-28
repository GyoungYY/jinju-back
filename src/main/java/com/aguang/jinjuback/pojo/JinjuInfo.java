package com.aguang.jinjuback.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;

/**
 * 返回页面的金句Info
 */
@JSONType(orders={"jinjuId","type","content","upVoteCount","downVoteCount","collectCount","commentCount",
        "upOrDownVote","isCollect","userId","username","photoUrl","createTime","updateTime"})
public class JinjuInfo implements Serializable {

    // 金句id
    private Integer jinjuId;

    // 金句类型
    private Integer type;

    // 金句内容
    private String content;

    // 点赞总数
    private Integer upVoteCount;

    // 点踩总数
    private Integer downVoteCount;

    // 收藏总数
    private Integer collectCount;

    // 评论总数
    private Integer commentCount;

    // 点赞或踩  1：赞、2：踩、其它
    private Integer upOrDownVote;

    // 是否收藏
    @JSONField(name = "isCollect")
    private boolean isCollect;

    // 金句创建用户id
    private Integer userId;

    // 金句创建用户名
    private String username;

    /* 头像 */
    private String photoUrl;

    // 金句创建时间
    private long createTime;

    // 金句更新时间
    private long updateTime;

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

    public Integer getUpOrDownVote() {
        return upOrDownVote;
    }

    public void setUpOrDownVote(Integer upOrDownVote) {
        this.upOrDownVote = upOrDownVote;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
