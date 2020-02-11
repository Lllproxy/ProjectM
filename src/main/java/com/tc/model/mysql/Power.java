package com.tc.model.mysql;

import javax.persistence.*;

public class Power {
    /**
     * 权限id
     */
    @Id
    @Column(name = "p_id")
    private String pId;

    /**
     * 权限说明
     */
    @Column(name = "p_dsc")
    private String pDsc;

    /**
     * 权限类型(w-资源，s-服务)
     */
    @Column(name = "p_type")
    private String pType;

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
     * 获取权限说明
     *
     * @return p_dsc - 权限说明
     */
    public String getpDsc() {
        return pDsc;
    }

    /**
     * 设置权限说明
     *
     * @param pDsc 权限说明
     */
    public void setpDsc(String pDsc) {
        this.pDsc = pDsc;
    }

    /**
     * 获取权限类型(w-资源，s-服务)
     *
     * @return p_type - 权限类型(w-资源，s-服务)
     */
    public String getpType() {
        return pType;
    }

    /**
     * 设置权限类型(w-资源，s-服务)
     *
     * @param pType 权限类型(w-资源，s-服务)
     */
    public void setpType(String pType) {
        this.pType = pType;
    }
}