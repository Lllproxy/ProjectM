package com.tc.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * 分页,跨域,校验，解决类（所有controller继承此类，解决跨域问题）
 *
 * @author: bocheng.luo
 * @date: 2018/12/31
 */
@CrossOrigin
public class PageUtils {
	protected int PAGE_INDEX = 0;
	protected int PAGE_SIZE = 20;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static boolean NeedToDo(List<String> colums){
		boolean YES=false;
		for (String colum:colums
			 ) {
			if (null!=colum &&"".equals(colum)){
				YES=true;
				break;
			}
		}
		return YES;
	 }

	/**
	 * 校验空值字段
	 * @param paramValue
	 * @return
	 */
	protected boolean validNullParam(String paramValue){
		boolean ISBREAK=false;
		if (StringUtils.isBlank(paramValue)||null==paramValue) {
			ISBREAK=true;
		}
		return ISBREAK;
	}

	/**
	 * 校验值是否为非法参数值
	 * @param paramValue
	 * @param dataType
	 * @return
	 */
	protected boolean isIllegalParam(String paramValue, String dataType){
		boolean ISBREAK=false;
		if (!"".equals(paramValue) && null!=paramValue){
		if("flaot".equals(dataType)){
			if (!paramValue.matches("^[0-9]+([.]{0,1}[0-9]+){0,1}$")) {
				ISBREAK=true;
			}
		}

		if("int".equals(dataType)){
			if (!paramValue.matches("^[0-9]+$")) {
				ISBREAK=true;
			}
		}
		}
		return ISBREAK;
	}


	public void exLimitParams(String page_index, String page_size, JSONObject params) {
		Integer sIndex;
		Integer fSize;
		try{
			page_index=StringUtils.isBlank(page_index) ? String.valueOf(PAGE_INDEX) :page_index;
			page_size=StringUtils.isBlank(page_size) ? String.valueOf(PAGE_SIZE) :page_size;
			sIndex=Integer.valueOf(page_index) * Integer.valueOf(page_size);
			fSize=Integer.valueOf(page_size);
			params.put("sIndex",sIndex);
			params.put("fSize",fSize);
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}
