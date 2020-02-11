package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "user_info")
public class UserInfo {
    /**
     * 用户id
     */
    @Id
    @Column(name = "u_id")
    private String uId;

    /**
     * 用户名
     */
    @Column(name = "u_name")
    private String uName;

    /**
     * 用户密码
     */
    @Column(name = "u_password")
    private String uPassword;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

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
     * 获取用户名
     *
     * @return u_name - 用户名
     */
    public String getuName() {
        return uName;
    }

    /**
     * 设置用户名
     *
     * @param uName 用户名
     */
    public void setuName(String uName) {
        this.uName = uName;
    }

    /**
     * 获取用户密码
     *
     * @return u_password - 用户密码
     */
    public String getuPassword() {
        return uPassword;
    }

    /**
     * 设置用户密码
     *
     * @param uPassword 用户密码
     */
    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
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