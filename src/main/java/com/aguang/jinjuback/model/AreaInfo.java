package com.aguang.jinjuback.model;

/**
 * 地址编码表(sys_area_info)
 */
public class AreaInfo {

    /* 地址编码 */
    private String areaCode;

    /* 地址名称 */
    private String areaName;

    /* 地址全名 */
    private String areaFullName;

    /* 上级编码 */
    private String parentCode;

    /* 级别：1:省,2:市,3:区,4:街道 */
    private String level;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaFullName() {
        return areaFullName;
    }

    public void setAreaFullName(String areaFullName) {
        this.areaFullName = areaFullName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
