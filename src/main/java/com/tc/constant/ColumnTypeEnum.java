package com.tc.constant;

public enum ColumnTypeEnum {

	VARCHAR2("VARCHAR2","VARCHAR"),  
    NUMBER("NUMBER","INT"),  
    DATE("DATE","DATETIME"),  
    CHAR("CHAR","CHAR");  
      
    private String oracleDbType;  
    private String mysqlDbType;  
      
    ColumnTypeEnum(String dbType,String javaType){  
        this.oracleDbType = dbType;  
        this.mysqlDbType = javaType;  
    }  
      
    public static String getColumnTypeEnumByDBType(String dbType){  
        for(ColumnTypeEnum columnTypeEnum:ColumnTypeEnum.values()){  
            if(columnTypeEnum.getOracleDbType().equals(dbType)){
                return columnTypeEnum.getMysqlDbType();  
            }   
        }  
       return dbType;  
    }

	public String getOracleDbType() {
		return oracleDbType;
	}

	public void setOracleDbType(String oracleDbType) {
		this.oracleDbType = oracleDbType;
	}

	public String getMysqlDbType() {
		return mysqlDbType;
	}

	public void setMysqlDbType(String mysqlDbType) {
		this.mysqlDbType = mysqlDbType;
	}  
 
    
   
}
