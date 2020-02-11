package com.tc.configurer;

import java.util.Properties;

import javax.sql.DataSource;

import com.tc.core.ProjectConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import com.github.pagehelper.PageHelper;
import com.tc.dydatasource.TargetDataSource;

/**
 * Mybatis & Mapper & PageHelper 配置
 */
//@Configuration
//@MapperScan(basePackages = {"com.mapabc.oracle.dao.oracle"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class MybatisOracleConfigurer {
 
	@Bean
	@TargetDataSource(name="oracle")
    public SqlSessionFactory sqlSessionFactory2(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(ProjectConstant.ORA_MODEL_PACKAGE);
        
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
        Resource[] res = resolver.getResources("classpath:mapper/oracle/*.xml");
        if (res == null || res.length==0)
        	return null;
        factory.setMapperLocations(res);
        return factory.getObject();
    }
	
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory2");
        mapperScannerConfigurer.setBasePackage(ProjectConstant.ORA_MAPPER_PACKAGE);
        
        //配置通用Mapper，详情请查阅官方文档
        Properties properties = new Properties();
        properties.setProperty("mappers", ProjectConstant.MAPPER_INTERFACE_REFERENCE);
        properties.setProperty("notEmpty", "false");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("IDENTITY", "ORACLE");
        mapperScannerConfigurer.setProperties(properties);
        
        return mapperScannerConfigurer;
    }

}

