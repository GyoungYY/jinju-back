package com.aguang.jinjuback.pojo.admin;

/**
 * 后台金句(爬虫)
 */
public class AdJinjuInfo {

    /* 主键 */
    private Integer id;

    /* 来源 */
    private Integer source;

    /* 内容 */
    private String content;

    /* 区分删除 1：删除 0：未删除 */
    private Integer isDelete;

    /* 区分删除 1：删除 0：未删除 */
    private Integer auditStatus;

    /* 创建时间 */
    private long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
