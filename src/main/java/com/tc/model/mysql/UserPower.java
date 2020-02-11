package com.tc.model.mysql;

import javax.persistence.*;

/**
 * @author bocheng.luo
 */
@Table(name = "user_power")
public class UserPower {
    @Id
    @Column(name = "up_id")
    private Integer upId;

    /**
     * 用户id
     */
    @Column(name = "u_id")
    private String uId;

    /**
     * 权限id
     */
    @Column(name = "p_id")
    private String pId;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

    /**
     * @return up_id
     */
    public Integer getUpId() {
        return upId;
    }

    /**
     * @param upId
     */
    public void setUpId(Integer upId) {
        this.upId = upId;
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
     * 获取权限id
     *
     * @return p_id - 权限id
     */
    public String getpId() {
        return pId;
    }

    /**
     * 设置权限id
     *
     * @param pId 权限id
     */
    public void setpId(String pId) {
        this.pId = pId;
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