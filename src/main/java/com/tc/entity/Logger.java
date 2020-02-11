package com.tc.entity;

/**
 * 日志实体 class
 *
 * @author: bocheng.luo
 * @date: 2019/3/19
 */
public class Logger {
    String uuId;
    String method;
    String localAddr;
    String status;

    public Long getWasteTimeMillis() {
        return wasteTimeMillis;
    }

    public void setWasteTimeMillis(Long wasteTimeMillis) {
        this.wasteTimeMillis = wasteTimeMillis;
    }

    Long wasteTimeMillis;

    public Logger() {
    }

    public Logger(String uuId, String method, String localAddr, String status, String user, String uri, String date,Long wasteTimeMillis) {
        this.uuId = uuId;
        this.method = method;
        this.localAddr = localAddr;
        this.status = status;
        this.user = user;
        this.uri = uri;
        this.date = date;
    }

    String user;

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String uri;
    String date;

}
