package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "power_web")
public class PowerWeb {
    /**
     * 页面权限id
     */
    @Id
    @Column(name = "pw_id")
    private String pwId;

    /**
     * 权限id
     */
    @Column(name = "p_id")
    private String pId;

    /**
     * 资源id
     */
    @Column(name = "w_id")
    private String wId;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

    /**
     * 获取页面权限id
     *
     * @return pw_id - 页面权限id
     */
    public String getPwId() {
        return pwId;
    }

    /**
     * 设置页面权限id
     *
     * @param pwId 页面权限id
     */
    public void setPwId(String pwId) {
        this.pwId = pwId;
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
     * 获取资源id
     *
     * @return w_id - 资源id
     */
    public String getwId() {
        return wId;
    }

    /**
     * 设置资源id
     *
     * @param wId 资源id
     */
    public void setwId(String wId) {
        this.wId = wId;
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