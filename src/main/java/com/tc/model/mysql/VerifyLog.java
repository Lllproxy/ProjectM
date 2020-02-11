package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "verify_log")
public class VerifyLog {
    /**
     * 唯一标识
     */
    @Id
    @Column(name = "v_id")
    private String vId;

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
     * 鉴定时间
     */
    @Column(name = "v_time")
    private String vTime;

    /**
     * 鉴定结果
     */
    @Column(name = "v_result")
    private Integer vResult;

    /**
     * 获取唯一标识
     *
     * @return v_id - 唯一标识
     */
    public String getvId() {
        return vId;
    }

    /**
     * 设置唯一标识
     *
     * @param vId 唯一标识
     */
    public void setvId(String vId) {
        this.vId = vId;
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
     * 获取鉴定时间
     *
     * @return v_time - 鉴定时间
     */
    public String getvTime() {
        return vTime;
    }

    /**
     * 设置鉴定时间
     *
     * @param vTime 鉴定时间
     */
    public void setvTime(String vTime) {
        this.vTime = vTime;
    }

    /**
     * 获取鉴定结果
     *
     * @return v_result - 鉴定结果
     */
    public Integer getvResult() {
        return vResult;
    }

    /**
     * 设置鉴定结果
     *
     * @param vResult 鉴定结果
     */
    public void setvResult(Integer vResult) {
        this.vResult = vResult;
    }
}