package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "usergroup_role")
public class UsergroupRole {
    /**
     * 用户组角色id
     */
    @Id
    @Column(name = "ugr_id")
    private String ugrId;

    /**
     * 用户组id
     */
    @Column(name = "ug_id")
    private String ugId;

    /**
     * 角色id
     */
    @Column(name = "r_id")
    private String rId;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

    /**
     * 获取用户组角色id
     *
     * @return ugr_id - 用户组角色id
     */
    public String getUgrId() {
        return ugrId;
    }

    /**
     * 设置用户组角色id
     *
     * @param ugrId 用户组角色id
     */
    public void setUgrId(String ugrId) {
        this.ugrId = ugrId;
    }

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
     * 获取角色id
     *
     * @return r_id - 角色id
     */
    public String getrId() {
        return rId;
    }

    /**
     * 设置角色id
     *
     * @param rId 角色id
     */
    public void setrId(String rId) {
        this.rId = rId;
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