package com.tc.model.mysql;



/**
 * 结合服务和资源权限的类
 * @author bocheng.luo
 */
public class PowerDetail {
    /**
     * 服务权限id
     */

    private String pswId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 权限类型：s-服务 w-资源
     */
    private String type;

    /**
     * 权限id
     */

    private String pId;

    /**
     * 服务id
     */

    private String swId;

    /**
     * 启用状态(1-启用 2-禁用)
     */

    private Integer isWork;

    /**
     * 获取服务权限id
     *
     * @return ps_id - 服务权限id
     */
    public String getPswId() {
        return pswId;
    }

    /**
     * 设置服务权限id
     *
     * @param pswId 服务权限id
     */
    public void setPswId(String pswId) {
        this.pswId = pswId;
    }

    /**
     * 获取权限id
     *
     * @return p_id - 权限id
     */
    public String getpId() {
        return pId;
    }

    /**
     * 设置权限id
     *
     * @param pId 权限id
     */
    public void setpId(String pId) {
        this.pId = pId;
    }

    /**
     * 获取服务id
     *
     * @return s_id - 服务id
     */
    public String getswId() {
        return swId;
    }

    /**
     * 设置服务id
     *
     * @param swId 服务id
     */
    public void setsId(String swId) {
        this.swId = swId;
    }

    /**
     * 获取启用状态(1-启用 2-禁用)
     *
     * @return is_work - 启用状态(1-启用 2-禁用)
     */
    public Integer getIsWork() {
        return isWork;
    }

    /**
     * 设置启用状态(1-启用 2-禁用)
     *
     * @param isWork 启用状态(1-启用 2-禁用)
     */
    public void setIsWork(Integer isWork) {
        this.isWork = isWork;
    }
}