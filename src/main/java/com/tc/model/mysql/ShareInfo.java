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
     * 产品名称
     */
    @Column(name = "share_name")
    private String shareName;

    /**
     * 产品类型：股票 基金
     */
    @Column(name = "share_type")
    private String shareType;

    /**
     * 交易所： 上交所  深交所 港交所
     */
    private String house;

    /**
     * 个股基金详细
     */
    @Column(name = "share_url")
    private String shareUrl;

    /**
     * 插入时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

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
     * 获取产品名称
     *
     * @return share_name - 产品名称
     */
    public String getShareName() {
        return shareName;
    }

    /**
     * 设置产品名称
     *
     * @param shareName 产品名称
     */
    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    /**
     * 获取产品类型：股票 基金
     *
     * @return share_type - 产品类型：股票 基金
     */
    public String getShareType() {
        return shareType;
    }

    /**
     * 设置产品类型：股票 基金
     *
     * @param shareType 产品类型：股票 基金
     */
    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    /**
     * 获取交易所： 上交所  深交所 港交所
     *
     * @return house - 交易所： 上交所  深交所 港交所
     */
    public String getHouse() {
        return house;
    }

    /**
     * 设置交易所： 上交所  深交所 港交所
     *
     * @param house 交易所： 上交所  深交所 港交所
     */
    public void setHouse(String house) {
        this.house = house;
    }

    /**
     * 获取个股基金详细
     *
     * @return share_url - 个股基金详细
     */
    public String getShareUrl() {
        return shareUrl;
    }

    /**
     * 设置个股基金详细
     *
     * @param shareUrl 个股基金详细
     */
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
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
}