package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "user_role")
public class UserRole {
    /**
     * 用户角色id
     */
    @Id
    @Column(name = "ur_id")
    private String urId;

    /**
     * 用户id
     */
    @Column(name = "u_id")
    private String uId;

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
     * 获取用户角色id
     *
     * @return ur_id - 用户角色id
     */
    public String getUrId() {
        return urId;
    }

    /**
     * 设置用户角色id
     *
     * @param urId 用户角色id
     */
    public void setUrId(String urId) {
        this.urId = urId;
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