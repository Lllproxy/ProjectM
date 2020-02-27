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

    /**
     * 插入时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

    /**
     * 交易所
     */
    @Column(name = "share_type")
    private String shareType;

    /**
     * 股票详细链接
     */
    @Column(name = "share_url")
    private String shareUrl;

    /**
     * 获取股票/基金代码
     *
     * @return share_id - 股票/基金代码
     */
    public String getShareId() {
        return shareId;
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

    /**
     * 获取插入时间
     *
     * @return insert_time - 插入时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * 设置插入时间
     *
     * @param insertTime 插入时间
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * 获取交易所
     *
     * @return share_type - 交易所
     */
    public String getShareType() {
        return shareType;
    }

    /**
     * 设置交易所
     *
     * @param shareType 交易所
     */
    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    /**
     * 获取股票详细链接
     *
     * @return share_url - 股票详细链接
     */
    public String getShareUrl() {
        return shareUrl;
    }

    /**
     * 设置股票详细链接
     *
     * @param shareUrl 股票详细链接
     */
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}