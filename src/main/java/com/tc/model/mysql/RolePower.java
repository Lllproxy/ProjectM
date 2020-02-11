package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "role_power")
public class RolePower {
    /**
     * 角色权限id
     */
    @Id
    @Column(name = "rp_id")
    private String rpId;

    /**
     * 角色id
     */
    @Column(name = "r_id")
    private String rId;

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
     * 获取角色权限id
     *
     * @return rp_id - 角色权限id
     */
    public String getRpId() {
        return rpId;
    }

    /**
     * 设置角色权限id
     *
     * @param rpId 角色权限id
     */
    public void setRpId(String rpId) {
        this.rpId = rpId;
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