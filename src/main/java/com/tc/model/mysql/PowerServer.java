package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "power_server")
public class PowerServer {
    /**
     * 服务权限id
     */
    @Id
    @Column(name = "ps_id")
    private String psId;

    /**
     * 权限id
     */
    @Column(name = "p_id")
    private String pId;

    /**
     * 服务id
     */
    @Column(name = "s_id")
    private String sId;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

    /**
     * 获取服务权限id
     *
     * @return ps_id - 服务权限id
     */
    public String getPsId() {
        return psId;
    }

    /**
     * 设置服务权限id
     *
     * @param psId 服务权限id
     */
    public void setPsId(String psId) {
        this.psId = psId;
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
     * 获取服务id
     *
     * @return s_id - 服务id
     */
    public String getsId() {
        return sId;
    }

    /**
     * 设置服务id
     *
     * @param sId 服务id
     */
    public void setsId(String sId) {
        this.sId = sId;
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