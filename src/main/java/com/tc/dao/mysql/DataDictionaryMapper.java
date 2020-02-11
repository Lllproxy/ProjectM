package com.tc.dao.mysql;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
 * 工具数据层映射接口
 *
 * @author:bocheng.luo
 * @date:2018/12/27
 */

public interface DataDictionaryMapper {
	/**
	 * 查询对应字典List
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryDictionList(JSONObject params);
	/**
	 * 查询空间描述类型
	 * @param params
	 * @return
	 */
	/**
	 * 加载字典数据
	 * @return
	 */
	public List<Map<String, Object>> loadAllDictionList();

	/**
	 * 查询字典说明
	 * @return
	 */
	List<Map<String, Object>> queryDataDescription();

	/**
	 * 运维字典表信息说明
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryOperationDescription(JSONObject params);

	/**
	 * 查询选定点。缓冲范围内。的设施设备
	 * @return
	 * @param paramss
	 */
    List<Map<String, Object>> queryFacilitiesByPointInBuffer(JSONObject paramss);

	/**
	 * 查询选定范围内。的设施设备
	 * @return
	 * @param paramss
	 */
	List<Map<String, Object>> queryFacilitiesBySelectArea(JSONObject paramss);

	/**
	 * 查询选定点。缓冲范围内。的路网对象
	 * @return
	 * @param paramss
	 */
	List<Map<String, Object>> queryRoadNetObjByPointBuffer(JSONObject paramss);
	/**
	 * 查询选定路网对象关联设备
	 * @return
	 * @param paramss
	 */
	List<Map<String, Object>> queryFacilitiesByRoadNetObj(JSONObject paramss);
	/**
	 * 查询选定设备关联路网对象
	 * @return
	 * @param paramss
	 */
	List<Map<String, Object>> queryRoadNetObjByFacilities(JSONObject paramss);

	/**
	 * 查询指定区域内rid
	 * @param paramss
	 * @return
	 */
	List<Map<String, Object>> queryRidsByArea(JSONObject paramss);
}
