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
}