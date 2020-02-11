package com.tc.configurer;

import java.util.Properties;

import javax.sql.DataSource;

import com.tc.core.ProjectConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import com.github.pagehelper.PageHelper;

/**
 * Mybatis & Mapper & PageHelper 配置
 * 
 * @author hfx
 *
 * @datetime 2018-8-30 上午11:46:21
 *
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.tc.dao.mysql"}, sqlSessionFactoryRef = "sqlSessionFactory1")
public class MybatisMysqlConfigurer {
	 
	
    @Bean
    public SqlSessionFactory sqlSessionFactory1(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(ProjectConstant.MYSQL_MODEL_PACKAGE);

        //配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("supportMethodsArguments", "true");//支持通过 Mapper 接口参数来传递分页参数
        pageHelper.setProperties(properties);
        
        //添加插件
        factory.setPlugins(new Interceptor[]{pageHelper});
        
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/mysql/*.xml"));
        return factory.getObject();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory1");
        mapperScannerConfigurer.setBasePackage(ProjectConstant.MYSQL_MAPPER_PACKAGE);

        //配置通用Mapper，详情请查阅官方文档
        Properties properties = new Properties();
        properties.setProperty("mappers", ProjectConstant.MAPPER_INTERFACE_REFERENCE);
        properties.setProperty("notEmpty", "false");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }

}

