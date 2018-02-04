package com.aguang.jinjuback.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 评论表(mw_comment)
 */
public class MeiwenComment {

    /* 主键 */
    private Integer id;

    /* 美文Id */
    @NotNull
    private Integer meiwenId;

    /* 用户Id */
    private Integer userId;

    /* 内容 */
    @NotNull(message = "评论内容不能为空")
    @Length(min = 1, max = 500, message = "字数必须在1~500之间")
    private String content;

    /* 创建时间 */
    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeiwenId() {
        return meiwenId;
    }

    public void setMeiwenId(Integer meiwenId) {
        this.meiwenId = meiwenId;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
