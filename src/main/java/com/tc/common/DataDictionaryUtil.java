package com.tc.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tc.dao.mysql.DataDictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * 
 * @作者： luobc
 * @日期： 2019-01-14
 * @时间： 上午11:18:07
 * @描述：字典分类代码查询
 */
@Component
public class DataDictionaryUtil  {
	@Autowired
	public DataDictionaryMapper dataDictionaryDao;
	public   static  DataDictionaryUtil dataDictionaryUtil;
	@PostConstruct
	//@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
	// 并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行
	public void init() {
		dataDictionaryUtil = this;
		dataDictionaryUtil.dataDictionaryDao = this.dataDictionaryDao;
	}
	//内存map
	private static List<Map<String, String>> DICTIONARY=new ArrayList<Map<String,String>>();
	private static List<Map<String, Object>> DATASICTIONLIST=new ArrayList<Map<String,Object>>();
//	static {
//	}

	public static void loadData(){
		//System.out.println("字典加载...");
		List<Map<String, Object>> DictionaryInfo = dataDictionaryUtil.dataDictionaryDao
				.loadAllDictionList();
		//System.out.println("字典加载..."+DictionaryInfo);
		for (Map<String, Object> map : DictionaryInfo) {
			String code=map.get("code").toString();
			String dict_code=map.get("dict_code").toString();
			String name=map.get("name").toString();
			Map<String, String> DICTIONARYMAP =new HashMap<String, String>();
			//拼接dict_code_code作为key值,映射存储name
			DICTIONARYMAP.put(dict_code+"_"+code, name);
			DICTIONARY.add(DICTIONARYMAP);
			//System.out.println("DICTIONARYMAP   "+DICTIONARYMAP);
			//保存dict_code作为key值得,映射存储map
			Map<String, Object> DATASICTIONLISTMAP =new HashMap<String, Object>();
			DATASICTIONLISTMAP.put(dict_code, map);
			DATASICTIONLIST.add(DATASICTIONLISTMAP);
		}
	}
	/**
	 * 查询对应字典name
	 * @param params
	 * @return
	 */
//	public static String queryDiction_One(JSONObject params) {
//		String DictionName=null;
//		try {
//			List<Map<String, Object>> dataType = dataDictionaryDao
//					.queryDictionList(params);
//			for (Map<String, Object> map : dataType) {
//				DictionName=map.get("name").toString();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return DictionName;
//	}
	/**
	 * 查询code_dict_code对应字典name
	 * @param params
	 * @return
	 */
	public static String queryDiction_One(JSONObject params) {
		String DictionName=null;
		String dict_code=params.getString("dict_code");
		String code=params.getString("code");
		//得到dict_code和code值拼接key从内存map里面取name值
		String key=dict_code+"_"+code;
		for (int i = 0; i < DICTIONARY.size(); i++) {
			if (null==DICTIONARY.get(i).get(key)) {
				continue;
			}
			DictionName=DICTIONARY.get(i).get(key);
		}
		return DictionName;
	}
	/**
	 * 查询对应字典List
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String queryDiction_List(JSONObject params) {
		JSONObject result = new JSONObject();
		JSONObject types = new JSONObject();
		String dict_code=params.getString("dict_code");
		String code=params.getString("code");
		Map<String, String> map=new HashMap<String, String>();
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		for (int i = 0; i < DATASICTIONLIST.size(); i++) {
			map=(Map<String, String>) DATASICTIONLIST.get(i).get(dict_code);
			if (null!=map ){
				if(!"".equals(code)&&null!=code){
					if(code.equals(map.get("code").toString())){
						list.add(map);
					}
				}else{
					list.add(map);
				}
			}
		}
			types.put("list", list);
			result.put("result", types);
			return result.toString();
	}

	/**
	 * 测试类
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject params=new JSONObject();
//		params.put("code", 110100);
		params.put("dict_code", "XZQH01");
		String DictionName=DataDictionaryUtil.queryDiction_List(params);
		System.out.println(DictionName);
	}

	/**
	 * 字典解释说明
	 * @return
	 */
	public List<Map<String,Object>> queryDataDescription() {

		return  dataDictionaryDao.queryDataDescription();
	}
	/**
	 * 字典解释说明
	 * @return
	 */
	public List<Map<String,Object>> queryOperationDescription(JSONObject params) {

		return  dataDictionaryDao.queryOperationDescription(params);
	}

	/**
	 * 更具给定点和指定查询对象查询附近buffer范围内的设施
	 * @param paramss
	 * @return
	 */
    public List<Map<String,Object>>  queryFacilitiesByPointInBuffer(JSONObject paramss) {
		return  dataDictionaryDao.queryFacilitiesByPointInBuffer(paramss);
    }

	public List<Map<String,Object>>  queryFacilitiesBySelectArea(JSONObject paramss) {
		return  dataDictionaryDao.queryFacilitiesBySelectArea(paramss);
	}

	public List<Map<String,Object>> queryRoadNetObjByPointBuffer(JSONObject paramss) {
		return  dataDictionaryDao.queryRoadNetObjByPointBuffer(paramss);
	}

	public List<Map<String,Object>> queryFacilitiesByRoadNetObj(JSONObject paramss) {
		return  dataDictionaryDao.queryFacilitiesByRoadNetObj(paramss);
	}
	public List<Map<String,Object>> queryRoadNetObjByFacilities(JSONObject paramss) {
		return  dataDictionaryDao.queryRoadNetObjByFacilities(paramss);
	}

	/**
	 * 查询code_dict_code对应字典name
	 * @param params
	 * @return
	 */
	public static String queryDiction_More(JSONObject params) {
		String DictionName=null;
		String dict_codes=params.getString("dict_codes");
		String code=params.getString("code");
		//得到dict_code和code值拼接key从内存map里面取name值
		String[] dicos=dict_codes.split(",");
		for (String dict_code:dicos
			 ) {
			String key=dict_code+"_"+code;
			for (int i = 0; i < DICTIONARY.size(); i++) {
				if (null==DICTIONARY.get(i).get(key)) {
					continue;
				}
				DictionName=DICTIONARY.get(i).get(key);
			}
		}
		return DictionName;
	}

	public List<Map<String,Object>> queryRidsByArea(JSONObject paramss) {
		String wkt = paramss.get("areaWkt").toString();

		JSONArray jsonArray = JSONArray.parseArray(wkt);
		// 将第一个点追加到最后，使区域形成一个闭环
		jsonArray.add(jsonArray.get(0));
		String geo = "POLYGON((";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONArray point = jsonArray.getJSONArray(i);
			if (i == jsonArray.size() - 1) {
				geo = geo + "" + point.getDouble(0) + " " + point.getDouble(1);
			} else {
				geo = geo + "" + point.getDouble(0) + " " + point.getDouble(1)+",";
			}
		}
		geo += "))";
		paramss.put("areaWkt",geo);

		return  dataDictionaryDao.queryRidsByArea(paramss);

	}
}
