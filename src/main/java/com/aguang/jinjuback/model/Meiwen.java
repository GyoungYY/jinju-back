package com.aguang.jinjuback.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 美文表（meiwen）
 */
public class Meiwen {

    /* 主键 */
    private Integer id;

    /* 类型 */
    @NotNull
    private Integer type;

    /* 标题 */
    @NotNull
    private String title;

    /* 摘要 */
    @NotNull
    private String summary;

    /* 封页图片 */
    @NotNull
    private String coverImgUrl;

    /* 内容 */
    @NotNull
    @Length(min = 1, max = 102400, message = "超长")
    private String content;

    /* 浏览总数 */
    private Integer browseCount;

    /* 收藏总数 */
    private Integer collectCount;

    /* 评论总数 */
    private Integer commentCount;

    /* 用户Id */
    private Integer userId;

    /* 创建时间 */
    private long createTime;

    /* 更新时间 */
    private long updateTime;

    /* 区分删除 1：删除 0：未删除 */
    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
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
