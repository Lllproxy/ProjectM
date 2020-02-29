package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "share_company")
public class ShareCompany {
    /**
     * 产品代码
     */
    @Id
    @Column(name = "share_id")
    private String shareId;

    /**
     * 注册资本(万元)
     */
    @Column(name = "reg_capital")
    private Float regCapital;

    /**
     * 注册地址
     */
    @Column(name = "reg_adress")
    private String regAdress;

    /**
     * 上市日期
     */
    @Column(name = "mar_date")
    private String marDate;

    /**
     * 成立日期
     */
    @Column(name = "bul_date")
    private String bulDate;

    /**
     * 总股本(万股)
     */
    @Column(name = "tot_shares")
    private Float totShares;

    /**
     * 所属区域
     */
    private String area;

    /**
     * 上市市场
     */
    private String maket;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 发行公司名称
     */
    @Column(name = "com_name_zh")
    private String comNameZh;

    /**
     * 公司英文名称
     */
    @Column(name = "com_name_en")
    private String comNameEn;

    /**
     * 发行公司简称
     */
    @Column(name = "com_s_name_zh")
    private String comSNameZh;

    /**
     * 法人代表
     */
    @Column(name = "corporate_rep")
    private String corporateRep;

    /**
     * 公司电话
     */
    @Column(name = "com_phone")
    private String comPhone;

    /**
     * 公司传真
     */
    @Column(name = "com_tax")
    private String comTax;

    /**
     * 公司网站
     */
    @Column(name = "com_website")
    private String comWebsite;

    /**
     * 经营范围
     */
    private String rang;

    /**
     * 公司简介
     */
    @Column(name = "com_desc")
    private String comDesc;

    /**
     * 获取产品代码
     *
     * @return share_id - 产品代码
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置产品代码
     *
     * @param shareId 产品代码
     */
    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    /**
     * 获取注册资本(万元)
     *
     * @return reg_capital - 注册资本(万元)
     */
    public Float getRegCapital() {
        return regCapital;
    }

    /**
     * 设置注册资本(万元)
     *
     * @param regCapital 注册资本(万元)
     */
    public void setRegCapital(Float regCapital) {
        this.regCapital = regCapital;
    }

    /**
     * 获取注册地址
     *
     * @return reg_adress - 注册地址
     */
    public String getRegAdress() {
        return regAdress;
    }

    /**
     * 设置注册地址
     *
     * @param regAdress 注册地址
     */
    public void setRegAdress(String regAdress) {
        this.regAdress = regAdress;
    }

    /**
     * 获取上市日期
     *
     * @return mar_date - 上市日期
     */
    public String getMarDate() {
        return marDate;
    }

    /**
     * 设置上市日期
     *
     * @param marDate 上市日期
     */
    public void setMarDate(String marDate) {
        this.marDate = marDate;
    }

    /**
     * 获取成立日期
     *
     * @return bul_date - 成立日期
     */
    public String getBulDate() {
        return bulDate;
    }

    /**
     * 设置成立日期
     *
     * @param bulDate 成立日期
     */
    public void setBulDate(String bulDate) {
        this.bulDate = bulDate;
    }

    /**
     * 获取总股本(万股)
     *
     * @return tot_shares - 总股本(万股)
     */
    public Float getTotShares() {
        return totShares;
    }

    /**
     * 设置总股本(万股)
     *
     * @param totShares 总股本(万股)
     */
    public void setTotShares(Float totShares) {
        this.totShares = totShares;
    }

    /**
     * 获取所属区域
     *
     * @return area - 所属区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置所属区域
     *
     * @param area 所属区域
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取上市市场
     *
     * @return maket - 上市市场
     */
    public String getMaket() {
        return maket;
    }

    /**
     * 设置上市市场
     *
     * @param maket 上市市场
     */
    public void setMaket(String maket) {
        this.maket = maket;
    }

    /**
     * 获取所属行业
     *
     * @return industry - 所属行业
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置所属行业
     *
     * @param industry 所属行业
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * 获取发行公司名称
     *
     * @return com_name_zh - 发行公司名称
     */
    public String getComNameZh() {
        return comNameZh;
    }

    /**
     * 设置发行公司名称
     *
     * @param comNameZh 发行公司名称
     */
    public void setComNameZh(String comNameZh) {
        this.comNameZh = comNameZh;
    }

    /**
     * 获取公司英文名称
     *
     * @return com_name_en - 公司英文名称
     */
    public String getComNameEn() {
        return comNameEn;
    }

    /**
     * 设置公司英文名称
     *
     * @param comNameEn 公司英文名称
     */
    public void setComNameEn(String comNameEn) {
        this.comNameEn = comNameEn;
    }

    /**
     * 获取发行公司简称
     *
     * @return com_s_name_zh - 发行公司简称
     */
    public String getComSNameZh() {
        return comSNameZh;
    }

    /**
     * 设置发行公司简称
     *
     * @param comSNameZh 发行公司简称
     */
    public void setComSNameZh(String comSNameZh) {
        this.comSNameZh = comSNameZh;
    }

    /**
     * 获取法人代表
     *
     * @return corporate_rep - 法人代表
     */
    public String getCorporateRep() {
        return corporateRep;
    }

    /**
     * 设置法人代表
     *
     * @param corporateRep 法人代表
     */
    public void setCorporateRep(String corporateRep) {
        this.corporateRep = corporateRep;
    }

    /**
     * 获取公司电话
     *
     * @return com_phone - 公司电话
     */
    public String getComPhone() {
        return comPhone;
    }

    /**
     * 设置公司电话
     *
     * @param comPhone 公司电话
     */
    public void setComPhone(String comPhone) {
        this.comPhone = comPhone;
    }

    /**
     * 获取公司传真
     *
     * @return com_tax - 公司传真
     */
    public String getComTax() {
        return comTax;
    }

    /**
     * 设置公司传真
     *
     * @param comTax 公司传真
     */
    public void setComTax(String comTax) {
        this.comTax = comTax;
    }

    /**
     * 获取公司网站
     *
     * @return com_website - 公司网站
     */
    public String getComWebsite() {
        return comWebsite;
    }

    /**
     * 设置公司网站
     *
     * @param comWebsite 公司网站
     */
    public void setComWebsite(String comWebsite) {
        this.comWebsite = comWebsite;
    }

    /**
     * 获取经营范围
     *
     * @return rang - 经营范围
     */
    public String getRang() {
        return rang;
    }

    /**
     * 设置经营范围
     *
     * @param rang 经营范围
     */
    public void setRang(String rang) {
        this.rang = rang;
    }

    /**
     * 获取公司简介
     *
     * @return com_desc - 公司简介
     */
    public String getComDesc() {
        return comDesc;
    }

    /**
     * 设置公司简介
     *
     * @param comDesc 公司简介
     */
    public void setComDesc(String comDesc) {
        this.comDesc = comDesc;
    }
}