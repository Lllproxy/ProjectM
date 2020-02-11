package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "usergroup_user")
public class UsergroupUser {
    /**
     * 用户组用户id
     */
    @Id
    @Column(name = "ugu_id")
    private String uguId;

    /**
     * 用户组id
     */
    @Column(name = "ug_id")
    private String ugId;

    /**
     * 用户id
     */
    @Column(name = "u_id")
    private String uId;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

    /**
     * 获取用户组用户id
     *
     * @return ugu_id - 用户组用户id
     */
    public String getUguId() {
        return uguId;
    }

    /**
     * 设置用户组用户id
     *
     * @param uguId 用户组用户id
     */
    public void setUguId(String uguId) {
        this.uguId = uguId;
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
     * 获取用户id
     *
     * @return u_id - 用户id
     */
    public String getuId() {
        return uId;
    }

    /**
     * 设置用户id
     *
     * @param uId 用户id
     */
    public void setuId(String uId) {
        this.uId = uId;
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