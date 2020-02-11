package com.tc.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.common.DataDictionaryUtil;
import com.tc.common.PageUtils;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 工具类
 *
 * @author: bocheng.luo
 * @date: 2018/12/25
 */
@RestController
@RequestMapping("/util")
@Api(tags = "工具Controller")
public class UtilsController extends PageUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DataDictionaryUtil dataDictionaryUtil;

    @ApiOperation("查询字典信息")
    @RequestMapping(value = "/directionary/queryDataDirectionary", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "对应值不传代表查询某类所有字典", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dict_code", value = "对应类型", required = false, dataType = "String")
    })
    public Result queryDataDirectionary(String code, String dict_code) {
        logger.debug("/area/customArea/queryCustomArea");
        try {
            JSONObject params = new JSONObject();
            //110100
            params.put("code", code);
            //XZQH01
            params.put("dict_code", dict_code);
            JSONObject resultList = new JSONObject();
            resultList.put("list", JSONObject.parse(DataDictionaryUtil.queryDiction_List(params)));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @ApiOperation("查询字典解释信息")
    @RequestMapping(value = "/directionary/queryDataDescription", method = RequestMethod.GET)
    public Result queryDataDescription() {
        logger.debug("/directionary/queryDataDescription");
        try {
            JSONObject resultList = new JSONObject();
            resultList.put("list", dataDictionaryUtil.queryDataDescription());
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @ApiOperation("查询运维字典表信息")
    @RequestMapping(value = "/directionary/queryOperationDescription", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "codeLevel1", value = "一级分类编码", required = false, dataType = "String"),
        @ApiImplicitParam(name = "nameLevel1", value = "一级分类名称", required = false, dataType = "String"),
        @ApiImplicitParam(name = "codeLevel2", value = "二级分类编码", required = false, dataType = "String"),
        @ApiImplicitParam(name = "nameLevel2", value = "二级分类名称", required = false, dataType = "String"),
        @ApiImplicitParam(name = "functionType", value = "功能类型:0:故障，1：到期预警", required = false, dataType = "String"),
    })
    public Result queryOperationDescription(String codeLevel1,String nameLevel1,String codeLevel2,String nameLevel2,String functionType) {
        logger.debug("/directionary/queryOperationDescription");
        try {
            JSONObject resultList = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("codeLevel1",codeLevel1);
            params.put("nameLevel1",nameLevel1);
            params.put("codeLevel2",codeLevel2);
            params.put("nameLevel2",nameLevel2);
            params.put("functionType",functionType);

            resultList.put("list", dataDictionaryUtil.queryOperationDescription(params));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    /**
     * 通用方法
     *
     */

    @ApiOperation("查询选定点。缓冲范围内。的设施设备")
    @RequestMapping(value = "/map/queryFacilitiesByPointInBuffer", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "设施设备类型://1.交通服务设施，2.//交通标志,3.//交通标志杆,4.//交通标线,5.公里桩,6.//其他设施,7.//分离设施,8.//防护设施,9.监控类型,10.诱导屏，11.信号机,12.流量监测，13.气象监测", required = true, dataType = "int", example = "1"),
            @ApiImplicitParam(name = "filter", value = "详细类型{交通服务设施:01 服务区 02 停车区 03 检查站 04 收费站|交通标志:-|交通标志杆: 01 单柱 02 双柱 03 悬臂 04 龙门架 05 附着|交通标线:01 指示标线  02 禁止标线 03 警告标线  99其他标线|公里桩:-|其他设施:1,示警桩, 2 锥桶 3 导向灯箱 4 减速丘 5 交通岗亭 6 防撞桶 7 交通岛顶点|分离设施:01 交通岛  02 防护栏  03 隔离墩  04 水马|防护设施:01 防护网  02 防眩设施 03 声屏障  04 驻足区|监控类型：01 普通监控  02 交通卡口 03 电子警察 04 卡电一体 05 视频流量检测 06 事件检测 99其他|诱导屏|信号机|流量监测|气象监测", required = true, dataType = "String"),
            @ApiImplicitParam(name = "point", value = "选定点POINT(1 -1)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "buffer", value = "缓冲区间单位：米", required = true, dataType = "float")
    })
    public Result queryFacilitiesByPointInBuffer(
            int type, String point, String filter, float buffer
    ) {
        logger.debug("/util/map/queryFacilitiesByPointInBuffer");
        try {
            JSONObject resultList = new JSONObject();
            JSONObject params = new JSONObject();
            JSONObject paramss = new JSONObject();
            params.put("type", type);
            params.put("filter", filter);
            params.put("buffer", buffer);
            params.put("point", point);
            paramss = getTypeTable(params);

            paramss.put("buffer", buffer);
            paramss.put("point", point);
            resultList.put("list", dataDictionaryUtil.queryFacilitiesByPointInBuffer(paramss));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    /**
     * 查询选定区域内的设施设备
     */
    @ApiOperation("查询选定区域内的设施设备")
    @RequestMapping(value = "/map/queryFacilitiesBySelectArea", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "设施设备类型://1.交通服务设施，2.//交通标志,3.//交通标志杆,4.//交通标线,5.公里桩,6.//其他设施,7.//分离设施,8.//防护设施,", required = true, dataType = "int", example = "1"),
            @ApiImplicitParam(name = "filter", value = "详细类型{交通服务设施:01 服务区 02 停车区 03 检查站 04 收费站|交通标志:-|交通标志杆: 01 单柱 02 双柱 03 悬臂 04 龙门架 05 附着|交通标线:01 指示标线  02 禁止标线 03 警告标线  99其他标线|公里桩:-|其他设施:1,示警桩, 2 锥桶 3 导向灯箱 4 减速丘 5 交通岗亭 6 防撞桶 7 交通岛顶点|分离设施:01 交通岛  02 防护栏  03 隔离墩  04 水马|防护设施:01 防护网  02 防眩设施 03 声屏障  04 驻足区|", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pointA", value = "选定点A POINT(1 -1)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pointB", value = "选定点B POINT(1 -1)", required = true, dataType = "String")
    })
    public Result queryFacilitiesBySelectArea(
            int type, String filter, String pointA, String pointB, float buffer
    ) {
        logger.debug("/util/map/queryFacilitiesBySelectArea");
        try {
            JSONObject resultList = new JSONObject();
            JSONObject params = new JSONObject();
            JSONObject paramss = new JSONObject();
            params.put("type", type);
            params.put("filter", filter);
            params.put("buffer", buffer);
            paramss = getTypeTable(params);
            String wkt = "";
            String[] alp = pointA.replace("POINT(", "").replace(")", "").split(" ");
            String[] blp = pointB.replace("POINT(", "").replace(")", "").split(" ");
            wkt = "POLYGON((" + alp[0] + " " + alp[1] + "," + blp[0] + " " + alp[1] + "," + blp[0] + " " + blp[1] + "," + alp[0] + " " + blp[1] + "," + alp[0] + " " + alp[1] + "))";
            paramss.put("buffer", buffer);
            //paramss.put("point", point);
            paramss.put("wkt", wkt);
            resultList.put("list", dataDictionaryUtil.queryFacilitiesBySelectArea(paramss));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    /**
     *
     * 通用方法
     */

    @ApiOperation("查询选定点。缓冲范围内。的路网对象")
    @RequestMapping(value = "/map/queryRoadNetObjByPointBuffer", method = RequestMethod.POST)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "路网类型：1://道路，2://道路方向，3://路段，4:://路口", required = true, dataType = "int", example = "1"),
            @ApiImplicitParam(name = "point", value = "选定点POINT(1 -1)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "buffer", value = "缓冲区间单位：米", required = true, dataType = "float")
    })
    public Result queryRoadNetObjByPointBuffer(
            int type, String point, float buffer
    ) {
        logger.debug("/util/map/queryFacilitiesByPointInBuffer/");
        try {
            JSONObject resultList = new JSONObject();
            JSONObject params = new JSONObject();
            JSONObject paramss = new JSONObject();
            params.put("type", type);
            params.put("buffer", buffer);
            params.put("point", point);
            paramss = getTypeTable2(params);
            paramss.put("buffer", buffer);
            paramss.put("point", point);
            resultList.put("list", dataDictionaryUtil.queryRoadNetObjByPointBuffer(paramss));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }


    /**
     * 查询选定路网对象关联设备
     * @param roadNetType
     * @param roadNetTypeId
     * @param facilitiesType
     * @param facilitiesTypeNext
     * @return
     */
    @ApiOperation("查询选定路网对象关联设备")
    @RequestMapping(value = "/map/queryFacilitiesByRoadNetObj", method = RequestMethod.POST)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roadNetType", value = "路网类型：1://道路，2://道路方向，3://路段", required = true, dataType = "int", example = "1"),
            @ApiImplicitParam(name = "roadNetTypeId", value = "路网对象id", required = true, dataType = "String", example = "1"),
            @ApiImplicitParam(name = "facilitiesType", value = "基础设施类型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "facilitiesTypeNext", value = "基础设施下级类型", required = true, dataType = "String")
    })
    public Result queryFacilitiesByRoadNetObj(
            int roadNetType, String roadNetTypeId, String facilitiesType, String facilitiesTypeNext
    ) {
        logger.debug("/util/map/queryFacilitiesByRoadNetObj");
        try {
            JSONObject resultList = new JSONObject();
            JSONObject params = new JSONObject();
            JSONObject paramss = new JSONObject();
            params.put("roadNetType", roadNetType);
            params.put("roadNetTypeId", roadNetTypeId);
            params.put("facilitiesType", facilitiesType);
            params.put("facilitiesTypeNext", facilitiesTypeNext);
            paramss = getTypeTable3(params);
//            paramss.put("buffer", buffer);
//            paramss.put("point", point);
            resultList.put("list", dataDictionaryUtil.queryFacilitiesByRoadNetObj(paramss));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    /**
     * 查询选定设备关联路网对象
     * @param roadNetType
     * @param facilitiesTypeId
     * @param facilitiesType
     * @param facilitiesTypeNext
     * @return
     */
    @ApiOperation("查询选定设备关联路网对象")
    @RequestMapping(value = "/map/queryRoadNetObjByFacilities", method = RequestMethod.POST)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roadNetType", value = "路网类型：1://道路，2://道路方向，3://路段", required = true, dataType = "int", example = "1"),
            @ApiImplicitParam(name = "facilitiesTypeId", value = "设备对象id", required = true, dataType = "String", example = "1"),
            @ApiImplicitParam(name = "facilitiesType", value = "基础设施类型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "facilitiesTypeNext", value = "基础设施下级类型", required = true, dataType = "String")
    })
    public Result queryRoadNetObjByFacilities(
            int roadNetType, String facilitiesTypeId, String facilitiesType, String facilitiesTypeNext
    ) {
        logger.debug("/util/map/queryRoadNetObjByFacilities");
        try {
            JSONObject resultList = new JSONObject();
            JSONObject params = new JSONObject();
            JSONObject paramss;
            params.put("roadNetType", roadNetType);
            params.put("facilitiesTypeId", facilitiesTypeId);
            params.put("facilitiesType", facilitiesType);
            params.put("facilitiesTypeNext", facilitiesTypeNext);
            paramss = getTypeTable4(params);
//            paramss.put("buffer", buffer);
//            paramss.put("point", point);
            resultList.put("list", dataDictionaryUtil.queryRoadNetObjByFacilities(paramss));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    /**
     * 查询选定区域内的rids
     */
    @ApiOperation("查询选定区域内的rids")
    @RequestMapping(value = "/map/queryRidsByArea", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "citycode", value = "城市代码", required = false, dataType = "String"),
            @ApiImplicitParam(name = "areaWkt", value = "区域坐标数据,例如：[[120.7,30.7],[120.7,30.7]]", required = true, dataType = "String")
    })
    public Result queryRidsByArea(
            String citycode , String areaWkt
    ) {
        logger.debug("/util/map/queryRidsByArea");
        try {
            JSONObject resultList = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("citycode",citycode);
            params.put("areaWkt",areaWkt);
            resultList.put("list", dataDictionaryUtil.queryRidsByArea(params));
            return ResultGenerator.genSuccessResult(resultList);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }


    /**
     * 根据类型获取动态查询表(路网对象和设施对象)和过滤字段
     * @param filtter
     * @return
     */
    private JSONObject getTypeTable4(JSONObject filtter) {
        {
            JSONObject parmar = new JSONObject();
            switch (Integer.valueOf(filtter.get("roadNetType").toString())) {
                //道路
                case 1:
                    parmar.put("road_table_name", "t_base_road_info");
                    break;
                //道路方向
                case 2:
                    parmar.put("road_table_name", "t_base_roaddir_info");
                    break;
                //路段
                case 3:
                    parmar.put("road_table_name", "t_base_roaddirseg_info");
                    break;
                default:
            }
            switch (Integer.valueOf(filtter.get("facilitiesType").toString())) {
                //交通服务设施-'设施类型 01,服务区 02 停车区 03 检查站 04 收费站',
                case 1:
                    parmar.put("facilities_table_name", "t_base_facility_service");
                    parmar.put("facilities_colums_name", "ID");
                    parmar.put("facilities_colums_name_next", "LEVEL");
                    //'设施类型 01,服务区 02 停车区 03 检查站 04 收费站',
                    break;
                //交通标志
                case 2:
                    parmar.put("facilities_table_name", "t_base_facility_traffic_sign");
                    parmar.put("facilities_colums_name", "TRAFFICSIGNGROUPID");
                    break;
                //交通标志杆
                case 3:
                    parmar.put("facilities_table_name", "t_base_facility_signpole");
                    parmar.put("facilities_colums_name", "ID");
                    parmar.put("facilities_colums_name_next", "TYPE");
                    //'标志杆类型: 01 单柱 02 双柱 03 悬臂 04 龙门架 05 附着',
                    break;
                //交通标线
                case 4:
                    parmar.put("facilities_table_name", "t_base_facility_traffic_markline");
                    parmar.put("facilities_colums_name", "MARKLINEID");
                    parmar.put("facilities_colums_name_next", "SIGNLEVEL1");
                    //'组合分类：01 指示标线  02 禁止标线 03 警告标线  99其他标线',
                    break;
                //公里桩
                case 5:
                    parmar.put("facilities_table_name", "t_base_facility_milestone");
                    parmar.put("facilities_colums_name", "MILESTONEID");
                    break;
                //其他设施
                case 6:
                    parmar.put("facilities_table_name", "t_base_facility_other");
                    parmar.put("facilities_colums_name", "ID");
                    //1,示警桩, 2 锥桶 3 导向灯箱 4 减速丘 5 交通岗亭 6 防撞桶 7 交通岛顶点
                    break;
                //分离设施
                case 7:
                    parmar.put("facilities_table_name", "t_base_facility_division");
                    parmar.put("facilities_colums_name", "DIVISIONID");
                    parmar.put("facilities_colums_name_next", "DIVLEVEL1");
                    //01 交通岛  02 防护栏  03 隔离墩  04 水马 ',
                    break;
                //防护设施
                case 8:
                    parmar.put("facilities_table_name", "t_base_facility_protect");
                    parmar.put("facilities_colums_name", "PROTECTID");
                    parmar.put("facilities_colums_name_next", "PROLEVEL1");
                    //01 防护网  02 防眩设施 03 声屏障  04 驻足区',
                    break;
                default:
            }
            return parmar;
        }
    }

    /**
     * 根据类型获取动态查询表(路网对象和设施对象)和过滤字段
     * @param filtter
     * @return
     */
    private JSONObject getTypeTable3(JSONObject filtter) {
        {
            JSONObject parmar = new JSONObject();
            switch (Integer.valueOf(filtter.get("roadNetType").toString())) {
                //道路
                case 1:
                    parmar.put("road_table_name", "t_base_road_info");
                    parmar.put("road_colum_name", "ROADID");
                    break;
                //道路方向
                case 2:
                    parmar.put("road_table_name", "t_base_roaddir_info");
                    parmar.put("road_colum_name", "ROADDIRID");
                    break;
                //路段
                case 3:
                    parmar.put("road_table_name", "t_base_roaddirseg_info");
                    parmar.put("road_colum_name", "ROADSEGID");
                    break;
                default:
            }
            switch (Integer.valueOf(filtter.get("facilitiesType").toString())) {
                //交通服务设施-'设施类型 01,服务区 02 停车区 03 检查站 04 收费站',
                case 1:
                    parmar.put("facilities_table_name", "t_base_facility_service");
                    parmar.put("facilities_colums_name", "LEVEL");
                    //'设施类型 01,服务区 02 停车区 03 检查站 04 收费站',
                    break;
                //交通标志
                case 2:
                    parmar.put("facilities_table_name", "t_base_facility_traffic_sign");
                    //parmar.put("colums_name","");
                    break;
                //交通标志杆
                case 3:
                    parmar.put("facilities_table_name", "t_base_facility_signpole");
                    parmar.put("facilities_colums_name", "TYPE");
                    //'标志杆类型: 01 单柱 02 双柱 03 悬臂 04 龙门架 05 附着',
                    break;
                //交通标线
                case 4:
                    parmar.put("facilities_table_name", "t_base_facility_traffic_markline");
                    parmar.put("facilities_colums_name", "SIGNLEVEL1");
                    //'组合分类：01 指示标线  02 禁止标线 03 警告标线  99其他标线',
                    break;
                //公里桩
                case 5:
                    parmar.put("facilities_table_name", "t_base_facility_milestone");
                    //parmar.put("facilities_colums_name","SIGNLEVEL1");
                    break;
                //其他设施
                case 6:
                    parmar.put("facilities_table_name", "t_base_facility_other");
                    parmar.put("facilities_colums_name", "");
                    //1,示警桩, 2 锥桶 3 导向灯箱 4 减速丘 5 交通岗亭 6 防撞桶 7 交通岛顶点
                    break;
                //分离设施
                case 7:
                    parmar.put("facilities_table_name", "t_base_facility_division");
                    parmar.put("facilities_colums_name", "DIVLEVEL1");
                    //01 交通岛  02 防护栏  03 隔离墩  04 水马 ',
                    break;
                //防护设施
                case 8:
                    parmar.put("facilities_table_name", "t_base_facility_protect");
                    parmar.put("facilities_colums_name", "PROLEVEL1");
                    //01 防护网  02 防眩设施 03 声屏障  04 驻足区',
                    break;
                default:
            }
            return parmar;
        }
    }

    /**
     * 根据类型获取动态查询表(路网对象)和过滤字段
     * @param filtter
     * @return
     */
    private JSONObject getTypeTable2(JSONObject filtter) {
        {
            JSONObject parmar = new JSONObject();
            switch (Integer.valueOf(filtter.get("type").toString())) {
                //道路
                case 1:
                    parmar.put("table_name", "t_base_road_info");
                    parmar.put("table_name_x", "t_base_road_geoinfo");
                    parmar.put("colum_name", "ROADID");
                    parmar.put("colum_name_x", "ROADID");
                    parmar.put("wkt_colums_name", "LINEINFO");
                    break;
                //道路方向
                case 2:
                    parmar.put("table_name", "t_base_roaddir_info");
                    parmar.put("table_name_x", "t_base_roaddir_geoinfo");
                    parmar.put("colum_name", "ROADDIRID");
                    parmar.put("colum_name_x", "ROADDIRID");
                    parmar.put("wkt_colums_name", "LINEINFO");
                    break;
                //路段
                case 3:
                    parmar.put("table_name", "t_base_roaddirseg_info");
                    parmar.put("table_name_x", "t_base_roadseg_geoinfo");
                    parmar.put("colum_name", "ROADSEGID");
                    parmar.put("colum_name_x", "ROADSEGID");
                    parmar.put("wkt_colums_name", "LINEINFO");
                    break;
                //路口
                case 4:
                    parmar.put("table_name", "t_base_cross_info");
                    parmar.put("table_name_x", "t_base_cross_geoinfo");
                    parmar.put("colum_name", "CROSSID");
                    parmar.put("colum_name_x", "CROSSID");
                    parmar.put("wkt_colums_name", "LINEINFO");
                    break;
                default:
            }
            return parmar;
        }
    }

    /**
     * 根据类型获取动态查询表(设备设施)和过滤字段
     * @param filtter
     * @return
     */
    private JSONObject getTypeTable(@PathVariable JSONObject filtter) {
        JSONObject parmar = new JSONObject();
        switch (Integer.valueOf(filtter.get("type").toString())) {
            //交通服务设施-'设施类型 01,服务区 02 停车区 03 检查站 04 收费站',
            case 1:
                parmar.put("table_name", "t_base_facility_service");
                parmar.put("colums_name", "LEVEL");
                parmar.put("wkt_colums_name", "pointinfo");
                //'设施类型 01,服务区 02 停车区 03 检查站 04 收费站',
                break;
            //交通标志
            case 2:
                parmar.put("table_name", "t_base_facility_traffic_sign");
                parmar.put("wkt_colums_name", "pointinfo");
                //parmar.put("colums_name","");
                break;
            //交通标志杆
            case 3:
                parmar.put("table_name", "t_base_facility_signpole");
                parmar.put("colums_name", "TYPE");
                parmar.put("wkt_colums_name", "pointinfo");
                //'标志杆类型: 01 单柱 02 双柱 03 悬臂 04 龙门架 05 附着',
                break;
            //交通标线
            case 4:
                parmar.put("table_name", "t_base_facility_traffic_markline");
                parmar.put("colums_name", "SIGNLEVEL1");
                parmar.put("wkt_colums_name", "pointinfo");
                //'组合分类：01 指示标线  02 禁止标线 03 警告标线  99其他标线',
                break;
            //公里桩
            case 5:
                parmar.put("table_name", "t_base_facility_milestone");
                //parmar.put("colums_name","SIGNLEVEL1");
                parmar.put("wkt_colums_name", "pointinfo");
                break;
            //其他设施
            case 6:
                parmar.put("table_name", "t_base_facility_other");
                parmar.put("colums_name", "");
                parmar.put("wkt_colums_name", "pointinfo");
                //1,示警桩, 2 锥桶 3 导向灯箱 4 减速丘 5 交通岗亭 6 防撞桶 7 交通岛顶点
                break;
            //分离设施
            case 7:
                parmar.put("table_name", "t_base_facility_division");
                parmar.put("colums_name", "DIVLEVEL1");
                parmar.put("wkt_colums_name", "pointinfo");
                //01 交通岛  02 防护栏  03 隔离墩  04 水马 ',
                break;
            //防护设施
            case 8:
                parmar.put("table_name", "t_base_facility_protect");
                parmar.put("colums_name", "PROLEVEL1");
                parmar.put("wkt_colums_name", "pointinfo");
                //01 防护网  02 防眩设施 03 声屏障  04 驻足区',
                break;
             //监控类型
            case 9:
                parmar.put("table_name", "t_base_facility_camera");
                parmar.put("wkt_colums_name", "pointinfo");
                break;
            //诱导屏
            case 10:
                parmar.put("table_name", "t_base_facility_variablesign");
                parmar.put("wkt_colums_name", "pointinfo");
                break;
            //信号机
            case 11:
                parmar.put("table_name", "t_base_facility_signalcontroller");
                parmar.put("wkt_colums_name", "location");
                break;
            //流量设备
            case 12:
                parmar.put("table_name", "t_base_facility_flowequip");
                parmar.put("wkt_colums_name", "pointinfo");
                break;
            //气象监测
            case 13:
                parmar.put("table_name", "t_base_facility_meteorology_detecteor");
                parmar.put("wkt_colums_name", "pointinfo");
                break;
             default:
        }
        return parmar;
    }


}