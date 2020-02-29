package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "share_erro")
public class ShareErro {
    private String url;

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}