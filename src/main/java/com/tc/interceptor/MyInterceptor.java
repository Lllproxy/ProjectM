package com.tc.interceptor;

import com.tc.common.CookiesUtils;
import com.tc.common.DBUtil;
import com.tc.entity.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bocheng.luo
 * @date:2019/3/19
 */
public class MyInterceptor implements HandlerInterceptor {
	
	private long sta = 0L;
	private long end = 0L;

	/**
	 * DispatcherServlet渲染视图之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object obj, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * Controller的方法调用之后，DispatcherServlet进行视图的渲染之前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
						   Object obj, ModelAndView model) throws Exception {
		
		end = System.currentTimeMillis();
		long wasteTimeMillis=end-sta;
//		System.out.println(request.getRequestURI() +"\t请求耗时(毫秒)："+ wasteTimeMillis);
		String cookies=request.getHeader("Cookie");
		System.out.println(request.getRequestURL() +"\t请求耗时(毫秒)："+ wasteTimeMillis+"     cookies:"+cookies);
//		System.out.println("请求方法："+request.getMethod());
//		System.out.println("请求地址："+request.getLocalAddr());
//		System.out.println("请求状态："+response.getStatus());
		myLogging(request, response,wasteTimeMillis);
	}

	private void myLogging(HttpServletRequest request, HttpServletResponse response,long wasteTimeMillis) {
		String user=null;
//		if(request.getRequestURI().toString().contains("queryDataDirectionary")){
//				return;
//	}
		if(null!= CookiesUtils.getCookieByName(request,"user")){
			System.out.println("用户 "+CookiesUtils.getCookieByName(request,"user"));
			user=CookiesUtils.getCookieByName(request,"user").toString();
		}
		Logger loggerr=new Logger();
		loggerr.setDate(DBUtil.toDateTime());
		loggerr.setUri(request.getRequestURI());
		if (user!=null){
			loggerr.setUser(user);
		}
		loggerr.setLocalAddr(request.getLocalAddr());
		loggerr.setMethod(request.getMethod());
		loggerr.setStatus(String.valueOf(response.getStatus()));
		loggerr.setWasteTimeMillis(wasteTimeMillis);
		//保存日志
		//DBUtil.loggingToDb(loggerr);
	}

	/**
	 * 该方法将在Controller处理之前进行调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object obj) throws Exception {
		
		sta = System.currentTimeMillis();
		return true;
	}

}
