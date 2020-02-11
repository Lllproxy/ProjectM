package com.tc;

import com.tc.common.DataDictionaryUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import com.tc.dydatasource.DynamicDataSourceRegister;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 *
 * @author bocheng.luo
 * @date 2020/02/11
 */
@EnableCaching
@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
@Import({DynamicDataSourceRegister.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        DataDictionaryUtil.loadData();
    }

}