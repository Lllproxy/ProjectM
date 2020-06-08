package com.tc.model.mysql;

import java.util.Date;
import javax.persistence.*;

@Table(name = "share_value_base")
public class ShareValueBase {
    /**
     * 股票/基金代码
     */
    @Id
    @Column(name = "share_id")
    private String shareId;

    /**
     * 发行价格
     */
    @Column(name = "push_value")
    private Float pushValue;

    /**
     * 每股净资产
     */
    @Column(name = "real_price_each")
    private Float realPriceEach;

    /**
     * 每股利润
     */
    @Column(name = "porfit_each")
    private Float porfitEach;

    /**
     * 创建时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取发行价格
     *
     * @return push_value - 发行价格
     */
    public Float getPushValue() {
        return pushValue;
    }

    /**
     * 设置发行价格
     *
     * @param pushValue 发行价格
     */
    public void setPushValue(Float pushValue) {
        this.pushValue = pushValue;
    }

    /**
     * 获取每股净资产
     *
     * @return real_price_each - 每股净资产
     */
    public Float getRealPriceEach() {
        return realPriceEach;
    }

    /**
     * 设置每股净资产
     *
     * @param realPriceEach 每股净资产
     */
    public void setRealPriceEach(Float realPriceEach) {
        this.realPriceEach = realPriceEach;
    }

    /**
     * 获取每股利润
     *
     * @return porfit_each - 每股利润
     */
    public Float getPorfitEach() {
        return porfitEach;
    }

    /**
     * 设置每股利润
     *
     * @param porfitEach 每股利润
     */
    public void setPorfitEach(Float porfitEach) {
        this.porfitEach = porfitEach;
    }

    /**
     * 获取创建时间
     *
     * @return insert_time - 创建时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * 设置创建时间
     *
     * @param insertTime 创建时间
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}