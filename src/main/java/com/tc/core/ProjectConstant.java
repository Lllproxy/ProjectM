package com.tc.core;

/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String BASE_PACKAGE = "com.tc";//项目基础包名称，根据自己公司的项目修改
     

    public static final String MYSQL_MODEL_PACKAGE = BASE_PACKAGE + ".model.mysql";//Model所在包
    public static final String MYSQL_MAPPER_PACKAGE = BASE_PACKAGE + ".dao.mysql";//Mapper所在包
    public static final String MYSQL_SERVICE_PACKAGE = BASE_PACKAGE + ".service.mysql";//Service所在包
    public static final String MYSQL_SERVICE_IMPL_PACKAGE = MYSQL_SERVICE_PACKAGE + ".impl";//ServiceImpl所在包

    public static final String MYSQL_MODEL_PACKAGE_SECOND = BASE_PACKAGE + ".model.mysecond";//Model所在包
    public static final String MYSQL_MAPPER_PACKAGE_SECOND = BASE_PACKAGE + ".dao.mysecond";//Mapper所在包
    public static final String MYSQL_SERVICE_PACKAGE_SECOND = BASE_PACKAGE + ".service.mysecond";//Service所在包
    public static final String MYSQL_SERVICE_IMPL_PACKAGE_SECOND = MYSQL_SERVICE_PACKAGE_SECOND + ".impl";//ServiceImpl所在包


    public static final String ORA_MODEL_PACKAGE = BASE_PACKAGE + ".model.oracle";//Model所在包
    public static final String ORA_MAPPER_PACKAGE = BASE_PACKAGE + ".dao.oracle";//Mapper所在包
    public static final String ORA_SERVICE_PACKAGE = BASE_PACKAGE + ".service.oracle";//Service所在包
    public static final String ORA_SERVICE_IMPL_PACKAGE = ORA_SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
 
    
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web.controller";//Controller所在包
    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名


}
