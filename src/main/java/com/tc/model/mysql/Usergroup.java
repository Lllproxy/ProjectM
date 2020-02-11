package com.tc.model.mysql;

import javax.persistence.*;

public class Usergroup {
    /**
     * 用户组id
     */
    @Id
    @Column(name = "ug_id")
    private String ugId;

    /**
     * 用户组名称
     */
    @Column(name = "ug_name")
    private String ugName;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

    /**
     * 获取用户组id
     *
     * @return ug_id - 用户组id
     */
    public String getUgId() {
        return ugId;
    }

    /**
     * 设置用户组id
     *
     * @param ugId 用户组id
     */
    public void setUgId(String ugId) {
        this.ugId = ugId;
    }

    /**
     * 获取用户组名称
     *
     * @return ug_name - 用户组名称
     */
    public String getUgName() {
        return ugName;
    }

    /**
     * 设置用户组名称
     *
     * @param ugName 用户组名称
     */
    public void setUgName(String ugName) {
        this.ugName = ugName;
    }

    /**
     * 获取启用状态(1-启用 2-禁用)
     *
     * @return is_work - 启用状态(1-启用 2-禁用)
     */
    public Integer getIsWork() {
        return isWork;
    }

    /**
     * 设置启用状态(1-启用 2-禁用)
     *
     * @param isWork 启用状态(1-启用 2-禁用)
     */
    public void setIsWork(Integer isWork) {
        this.isWork = isWork;
    }
}