package com.tc.model.mysql;

import java.util.Date;
import javax.persistence.*;

@Table(name = "share_info")
public class ShareInfo {
    /**
     * 股票/基金代码
     */
    @Id
    @Column(name = "share_id")
    private String shareId;

    /**
     * 股票名称
     */
    @Column(name = "share_name")
    private String shareName;

    public String getShareId() {
        return shareId;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    /**
     * 交易所
     */
    @Column(name = "share_type")
    private String shareType;

    /**
     * 股票基金详细链接
     */
    @Column(name = "share_url")
    private String shareUrl;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }


    /**
     * 获取股票/基金代码
     *
     * @return share_id - 股票/基金代码
     * @param shareId
     */
    public String getShareId(String shareId) {
        return this.shareId;
    }

    /**
     * 设置股票/基金代码
     *
     * @param shareId 股票/基金代码
     */
    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    /**
     * 获取股票名称
     *
     * @return share_name - 股票名称
     */
    public String getShareName() {
        return shareName;
    }

    /**
     * 设置股票名称
     *
     * @param shareName 股票名称
     */
    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

}