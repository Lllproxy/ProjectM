package com.tc.model.mysql;

import javax.persistence.*;

@Table(name = "big_luck_all")
public class BigLuckAll {
    /**
     * 期数
     */
    @Id
    private String number;

    /**
     * 红1
     */
    @Column(name = "red_1")
    private String red1;

    @Column(name = "red_2")
    private String red2;

    @Column(name = "red_3")
    private String red3;

    @Column(name = "red_4")
    private String red4;

    @Column(name = "red_5")
    private String red5;

    @Column(name = "blue_1")
    private String blue1;

    @Column(name = "blue_2")
    private String blue2;

    /**
     * 红球和数
     */
    @Column(name = "r_tol")
    private String rTol;

    /**
     * 篮球和数
     */
    @Column(name = "b_tol")
    private String bTol;

    /**
     * 获取期数
     *
     * @return number - 期数
     */
    public String getNumber() {
        return number;
    }

    /**
     * 设置期数
     *
     * @param number 期数
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 获取红1
     *
     * @return red_1 - 红1
     */
    public String getRed1() {
        return red1;
    }

    /**
     * 设置红1
     *
     * @param red1 红1
     */
    public void setRed1(String red1) {
        this.red1 = red1;
    }

    /**
     * @return red_2
     */
    public String getRed2() {
        return red2;
    }

    /**
     * @param red2
     */
    public void setRed2(String red2) {
        this.red2 = red2;
    }

    /**
     * @return red_3
     */
    public String getRed3() {
        return red3;
    }

    /**
     * @param red3
     */
    public void setRed3(String red3) {
        this.red3 = red3;
    }

    /**
     * @return red_4
     */
    public String getRed4() {
        return red4;
    }

    /**
     * @param red4
     */
    public void setRed4(String red4) {
        this.red4 = red4;
    }

    /**
     * @return red_5
     */
    public String getRed5() {
        return red5;
    }

    /**
     * @param red5
     */
    public void setRed5(String red5) {
        this.red5 = red5;
    }

    /**
     * @return blue_1
     */
    public String getBlue1() {
        return blue1;
    }

    /**
     * @param blue1
     */
    public void setBlue1(String blue1) {
        this.blue1 = blue1;
    }

    /**
     * @return blue_2
     */
    public String getBlue2() {
        return blue2;
    }

    /**
     * @param blue2
     */
    public void setBlue2(String blue2) {
        this.blue2 = blue2;
    }

    /**
     * 获取红球和数
     *
     * @return r_tol - 红球和数
     */
    public String getrTol() {
        return rTol;
    }

    /**
     * 设置红球和数
     *
     * @param rTol 红球和数
     */
    public void setrTol(String rTol) {
        this.rTol = rTol;
    }

    /**
     * 获取篮球和数
     *
     * @return b_tol - 篮球和数
     */
    public String getbTol() {
        return bTol;
    }

    /**
     * 设置篮球和数
     *
     * @param bTol 篮球和数
     */
    public void setbTol(String bTol) {
        this.bTol = bTol;
    }
}