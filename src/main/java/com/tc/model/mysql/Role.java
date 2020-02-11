package com.tc.model.mysql;

import javax.persistence.*;

public class Role {
    /**
     * 角色id
     */
    @Id
    @Column(name = "r_id")
    private String rId;

    /**
     * 角色名称
     */
    @Column(name = "r_name")
    private String rName;

    /**
     * 父角色id
     */
    @Column(name = "p_r_id")
    private String pRId;

    /**
     * 启用状态(1-启用 2-禁用)
     */
    @Column(name = "is_work")
    private Integer isWork;

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
     * 获取角色名称
     *
     * @return r_name - 角色名称
     */
    public String getrName() {
        return rName;
    }

    /**
     * 设置角色名称
     *
     * @param rName 角色名称
     */
    public void setrName(String rName) {
        this.rName = rName;
    }

    /**
     * 获取父角色id
     *
     * @return p_r_id - 父角色id
     */
    public String getpRId() {
        return pRId;
    }

    /**
     * 设置父角色id
     *
     * @param pRId 父角色id
     */
    public void setpRId(String pRId) {
        this.pRId = pRId;
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