package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "web_info")
public class WebInfo {
    /**
     * 资源id
     */
    @Id
    @Column(name = "w_id")
    private String wId;

    /**
     * 资源id
     */
    private String url;

    /**
     * 资源状态
     */
    @Column(name = "w_status")
    private Integer wStatus;

    /**
     * 资源说明
     */
    @Column(name = "w_dsc")
    private String wDsc;

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
     * 获取资源id
     *
     * @return url - 资源id
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置资源id
     *
     * @param url 资源id
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取资源状态
     *
     * @return w_status - 资源状态
     */
    public Integer getwStatus() {
        return wStatus;
    }

    /**
     * 设置资源状态
     *
     * @param wStatus 资源状态
     */
    public void setwStatus(Integer wStatus) {
        this.wStatus = wStatus;
    }

    /**
     * 获取资源说明
     *
     * @return w_dsc - 资源说明
     */
    public String getwDsc() {
        return wDsc;
    }

    /**
     * 设置资源说明
     *
     * @param wDsc 资源说明
     */
    public void setwDsc(String wDsc) {
        this.wDsc = wDsc;
    }
}