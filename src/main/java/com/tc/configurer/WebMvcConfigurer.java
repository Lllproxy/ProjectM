package com.tc.configurer;


import com.tc.interceptor.MyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC 配置
 * 
 * @author bocheng.luo
 * @date:2019/3/19
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.profiles.active}")
    /**
     *当前激活的配置文件
     */
    private String env;
//    //使用阿里 FastJson 作为JSON MessageConverter
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
//                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
//                SerializerFeature.WriteNullNumberAsZero);//Number null -> 0
//        converter.setFastJsonConfig(config);
//        converter.setDefaultCharset(Charset.forName("UTF-8"));
//        converters.add(converter);
//    }
//
//
//    //统一异常处理
//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        exceptionResolvers.add(new HandlerExceptionResolver() {
//            @Override
//            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
//                Result result = new Result();
//                //业务失败的异常，如“账号或密码错误”
//                if (e instanceof ServiceException) {
//                    result.setCode(ResultCode.FAIL).setMsg(e.getMessage());
//                    logger.info(e.getMessage());
//                } else if (e instanceof NoHandlerFoundException) {
//                    result.setCode(ResultCode.NOT_FOUND).setMsg("接口 [" + request.getRequestURI() + "] 不存在");
//                } else if (e instanceof ServletException) {
//                    result.setCode(ResultCode.FAIL).setMsg(e.getMessage());
//                } else {
//                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
//                    String message;
//                    if (handler instanceof HandlerMethod) {
//                        HandlerMethod handlerMethod = (HandlerMethod) handler;
//                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
//                                request.getRequestURI(),
//                                handlerMethod.getBean().getClass().getName(),
//                                handlerMethod.getMethod().getName(),
//                                e.getMessage());
//                    } else {
//                        message = e.getMessage();
//                    }
//                    logger.error(message, e);
//                }
//                responseResult(response, result);
//                return new ModelAndView();
//            }
//
//        });
//    }
//
//    /**
//     *解决跨域问题
//     */
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //为根目录的全部请求，也可以设置为"/user/**"，这意味着是user目录下的所有请求
//        registry.addMapping("/**");
//    }
//

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加耗时输出拦截器
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
        System.out.println("拦截器注册成功");
        super.addInterceptors(registry);
    }
    
//    private boolean validAccessKey(HttpServletRequest request){
//    	 String ak = request.getParameter("ak");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
//         if (StringUtils.isEmpty(ak)) {
//             return false;
//         }
//
//    	Map<String, String> paraMap = new HashMap<String, String>();
//		paraMap.put("keyCode", ak);
//		paraMap.put("encode", "utf-8");
//
////		String result = null;
////		try {
////			result = HttpUtil.send(checkLicUrl, "GET", paraMap, null);
////
////			JSONObject json = JSONObject.parseObject(result);
////			Map<?, ?> map = json.toJavaObject(json, Map.class);
////			if(StringUtils.isEmpty(result) || !map.get("status").equals("0")){
////				return false;
////			}
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//
//		return true;
//    }
//
//    private void responseResult(HttpServletResponse response, Result result) {
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-type", "application/json;charset=UTF-8");
//        response.setStatus(200);
//        try {
//            response.getWriter().write(JSON.toJSONString(result));
//        } catch (IOException ex) {
//            logger.error(ex.getMessage());
//        }
//    }
//
//    /**
//     * 一个简单的签名认证，规则：
//     * 1. 将请求参数按ascii码排序
//     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
//     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
//     */
////    private boolean validateSign(HttpServletRequest request) {
////        String requestSign = request.getParameter("sign");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
////        if (StringUtils.isEmpty(requestSign)) {
////            return false;
////        }
////        List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
////        keys.remove("sign");//排除sign参数
////        Collections.sort(keys);//排序
////
////        StringBuilder sb = new StringBuilder();
////        for (String key : keys) {
////            sb.append(key).append("=").append(request.getParameter(key)).append("&");//拼接字符串
////        }
////        String linkString = sb.toString();
////        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'
////
////        String secret = "Potato";//密钥，自己修改
////        String sign = DigestUtils.md5Hex(linkString + secret);//混合密钥md5
////
////        return StringUtils.equals(sign, requestSign);//比较
////    }
//
//    private String getIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        // 如果是多级代理，那么取第一个ip为客户端ip
//        if (ip != null && ip.indexOf(",") != -1) {
//            ip = ip.substring(0, ip.indexOf(",")).trim();
//        }
//
//        return ip;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
