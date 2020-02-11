package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "server_info")
public class ServerInfo {
    /**
     * 服务id
     */
    @Id
    @Column(name = "s_id")
    private String sId;

    /**
     * 服务接口
     */
    private String api;

    /**
     * 服务状态
     */
    @Column(name = "s_status")
    private Integer sStatus;

    /**
     * 服务说明
     */
    @Column(name = "s_dsc")
    private String sDsc;

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
     * 获取服务接口
     *
     * @return api - 服务接口
     */
    public String getApi() {
        return api;
    }

    /**
     * 设置服务接口
     *
     * @param api 服务接口
     */
    public void setApi(String api) {
        this.api = api;
    }

    /**
     * 获取服务状态
     *
     * @return s_status - 服务状态
     */
    public Integer getsStatus() {
        return sStatus;
    }

    /**
     * 设置服务状态
     *
     * @param sStatus 服务状态
     */
    public void setsStatus(Integer sStatus) {
        this.sStatus = sStatus;
    }

    /**
     * 获取服务说明
     *
     * @return s_dsc - 服务说明
     */
    public String getsDsc() {
        return sDsc;
    }

    /**
     * 设置服务说明
     *
     * @param sDsc 服务说明
     */
    public void setsDsc(String sDsc) {
        this.sDsc = sDsc;
    }
}