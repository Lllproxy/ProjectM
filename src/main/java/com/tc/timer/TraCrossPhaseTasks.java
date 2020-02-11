package com.tc.timer;

import com.tc.timer.thread.CrossOptimezeRecoveryThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import com.tc.common.DateTimeUtils;

/**
 * 实时优化（交通路口信号灯配时优化）
 * 
 */
//@Component
//@Configurable
//@EnableScheduling
public class TraCrossPhaseTasks implements CommandLineRunner {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run(String... args) {
		try {
			while(true) {
				try{
					long sta = System.currentTimeMillis();
					handler();
					long end = System.currentTimeMillis();
					logger.info(DateTimeUtils.getCurrentTime(null)+ "实时优化\t耗时（豪秒）："+ (end - sta));
				}catch(Exception e){
					logger.error("", e);
					logger.error("交通路口信号灯配时优化定时器发生异常", e);
				}
				Thread.sleep(1000 * 3);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
	}
	 
	/**
	 * 业务处理
	 */
	public void handler() {
		
		Thread thread = new CrossOptimezeRecoveryThread();
		thread.start();
		
	}
	
}
