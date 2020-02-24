package com.tc.model.mysql;


/**
 * @author luobocheng
 * @date 2020/2/24
 * @dsrc
 */
public class PowerUnionAll {
     Power power;
     PowerDetail powerDetail;

    public PowerDetail getPowerDetail() {
        return powerDetail;
    }

    public void setPowerDetail(PowerDetail powerDetail) {
        this.powerDetail = powerDetail;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public PowerUnionAll(Power power, PowerDetail powerDetail) {
        this.power=power;
        this.powerDetail=powerDetail;
    }
}
