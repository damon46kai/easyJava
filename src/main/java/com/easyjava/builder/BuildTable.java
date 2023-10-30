package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildTable {

    private static final Logger logger= LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;

    private static String SQL_SHOW_TABLE_STATUS = "show table status";

    private static String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";

    static {
        String driverName = PropertiesUtils.getString("db.driver.name");
        String url = PropertiesUtils.getString("db.url");
        String user = PropertiesUtils.getString("db.username");
        String password = PropertiesUtils.getString("db.password");
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.error("数据库链接失败", e );
        }

    }
        public static void getTables(){
            PreparedStatement ps = null;
            ResultSet tableResult = null;


            List<TableInfo> tableInfoList = new ArrayList();
            try{
                ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
                tableResult = ps.executeQuery();
                while(tableResult.next()){
                    String tableName = tableResult.getString("name");
                    String comment = tableResult.getString("comment");
                    //logger.info("tableName：{}，comment:{}",tableName, comment);


                    String beanName = tableName;
                    if(Constants.IGNORE_TABLE_PREFIX){
                        beanName = tableName.substring(beanName.indexOf("_")+1);
                    }

                    beanName = processField(beanName, true);


                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setTableName(tableName);
                    tableInfo.setBeanName(beanName);
                    tableInfo.setComment(comment);

                    tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_PARAM);

                    List<FieldInfo> fieldInfoList = readFieldInfo(tableInfo);

                    logger.info(fieldInfoList.toString());
                //    logger.info("table:{},comment:{},JavaBean:{},JavaParamBean:{}",tableInfo.getTableName(),tableInfo.getComment(),tableInfo.getBeanName(),tableInfo.getBeanParamName());
                readFieldInfo(tableInfo);
                }
            }catch (Exception e){
                logger.error("读取表失败");
            }finally {
                if(tableResult!=null){
                    try {
                        tableResult.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(ps!=null){
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(conn!=null){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


        private static List<FieldInfo> readFieldInfo(TableInfo tableInfo) {
            PreparedStatement ps = null;
            ResultSet fieldResult = null;

            List<FieldInfo> fieldInfoList = new ArrayList();
            try{
                ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
                fieldResult = ps.executeQuery();
                while(fieldResult.next()){
                    String field = fieldResult.getString("field");
                    String type = fieldResult.getString("type");
                    String extra = fieldResult.getString("extra");
                    String comment = fieldResult.getString("comment");
                    if(type.indexOf("(")>0){
                        type = type.substring(0, type.indexOf("("));
                    }

                    String propertyName  = processField(field, false);
                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfoList.add(fieldInfo);

                    fieldInfo.setFieldName(field);
                    fieldInfo.setComment(comment);
                    fieldInfo.setSqlType(type);
                    fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra)?true: false);
                    fieldInfo.setPropertyName(propertyName);
                    fieldInfo.setJavaType(processJavaType(type));

                    if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)){
                        tableInfo.setHaveBigDecimal(true);
                    }
                    if(ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)){
                        tableInfo.setHaveDate(true);
                    }
                    if(ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)){
                        tableInfo.setHaveBigDecimal(true);
                    }

                    //logger.info("javaType:{}", fieldInfo.getJavaType());
                   // logger.info("field:{},propertyName:{},type:{},extra:{},comment:{}",field, type, extra, comment);

                 }
            }catch (Exception e){
                logger.error("读取表失败");
            }finally {
                if(fieldResult!=null){
                    try {
                        fieldResult.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(ps!=null){
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
                return fieldInfoList;
        }

        private static String processField(String field, Boolean upperCaseFirstLetter){
            StringBuffer sb = new StringBuffer();

            String[] fields = field.split("_");
            sb.append(upperCaseFirstLetter? StringUtils.upperCaseFirstLetter(fields[0]) :fields[0]);

            for(int i=1, len = fields.length; i<len; i++) {
                sb.append(StringUtils.upperCaseFirstLetter(fields[i]));
            }
            return sb.toString();
        }


        private static String processJavaType(String type){
            if(ArrayUtils.contains(Constants.SQL_INTEGER_TYPE, type)){
                return "Integer";
            }else if(ArrayUtils.contains(Constants.SQL_LONG_TYPE, type)){
                return "Long";
            }else if(ArrayUtils.contains(Constants.SQL_STRING_TYPE, type)){
                return "String";
            }else if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,type) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)){
                return "Date";
            }else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)){
              return "BigDecimal";
            } else{
                throw new RuntimeException("无法识别的类型" + type);
            }
        }
}
