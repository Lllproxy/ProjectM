package com.tc.configurer;

import org.springframework.web.bind.annotation.ControllerAdvice; 
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice; 
  
/**
 * 
 * @author hfx
 *
 * @datetime 2018-8-30 上午11:46:10
 *
 * @version 1.0
 */
@ControllerAdvice(basePackages = "com.tc.web.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{ 
  
  public JsonpAdvice() {
    super("callback","jsonp"); 
  } 
} 