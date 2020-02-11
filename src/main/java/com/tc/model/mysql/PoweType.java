package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "powe_type")
public class PoweType {
    /**
     * 权限类型(w-资源，s-服务)
     */
    @Id
    @Column(name = "p_type")
    private String pType;

    /**
     * 权限类型字典id
     */
    @Column(name = "pt_id")
    private String ptId;

    /**
     * 权限类型名称
     */
    @Column(name = "p_type_name")
    private String pTypeName;

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

    /**
     * 获取权限类型字典id
     *
     * @return pt_id - 权限类型字典id
     */
    public String getPtId() {
        return ptId;
    }

    /**
     * 设置权限类型字典id
     *
     * @param ptId 权限类型字典id
     */
    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    /**
     * 获取权限类型名称
     *
     * @return p_type_name - 权限类型名称
     */
    public String getpTypeName() {
        return pTypeName;
    }

    /**
     * 设置权限类型名称
     *
     * @param pTypeName 权限类型名称
     */
    public void setpTypeName(String pTypeName) {
        this.pTypeName = pTypeName;
    }
}