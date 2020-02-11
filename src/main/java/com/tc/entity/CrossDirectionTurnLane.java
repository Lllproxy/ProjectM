package com.tc.entity;


import java.util.List;

/**
 * @description: 路口方向下转向信息的车道实体
 * @author wm
 * @date 2019/5/28
 */
public class CrossDirectionTurnLane {

    private String crossId;
    private String scrossDirId;
    private String ecrossDirId;
    private String turnType;
    private String laneJsonArray;
    private List<TurnLane> laneList;

    public String getCrossId() {
        return crossId;
    }

    public void setCrossId(String crossId) {
        this.crossId = crossId;
    }

    public String getScrossDirId() {
        return scrossDirId;
    }

    public void setScrossDirId(String scrossDirId) {
        this.scrossDirId = scrossDirId;
    }

    public String getEcrossDirId() {
        return ecrossDirId;
    }

    public void setEcrossDirId(String ecrossDirId) {
        this.ecrossDirId = ecrossDirId;
    }

    public String getTurnType() {
        return turnType;
    }

    public void setTurnType(String turnType) {
        this.turnType = turnType;
    }

    public String getLaneJsonArray() {
        return laneJsonArray;
    }

    public void setLaneJsonArray(String laneJsonArray) {
        this.laneJsonArray = laneJsonArray;
    }

    public List<TurnLane> getLaneList() {
        return laneList;
    }

    public void setLaneList(List<TurnLane> laneList) {
        this.laneList = laneList;
    }

    /**
     * 转向车道信息
     */
    public static class TurnLane {
        /**
         * 车道id
         */
        private String laneId;
        /**
         * 车道编号
         */
        private String laneNo;
        /**
         * 车道权重
         */
        private String weight;

        public String getLaneId() {
            return laneId;
        }
        public void setLaneId(String laneId) {
            this.laneId = laneId;
        }
        public String getWeight() {
            return weight;
        }
        public void setWeight(String weight) {
            this.weight = weight;
        }
        public String getLaneNo() {
            return laneNo;
        }
        public void setLaneNo(String laneNo) {
            this.laneNo = laneNo;
        }
    }
}
