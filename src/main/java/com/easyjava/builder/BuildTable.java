package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildTable {

    private static final Logger logger= LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;

    private static String SQL_SHOW_TABLE_STATUS = "show table status";
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

                    logger.info("table:{},comment:{},JavaBean:{},JavaParamBean:{}",tableInfo.getTableName(),tableInfo.getComment(),tableInfo.getBeanName(),tableInfo.getBeanParamName());
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


        private static String processField(String field, Boolean upperCaseFirstLetter){
            StringBuffer sb = new StringBuffer();

            String[] fields = field.split("_");
            sb.append(upperCaseFirstLetter? StringUtils.upperCaseFirstLetter(fields[0]) :fields[0]);

            for(int i=1, len = fields.length; i<len; i++) {
                sb.append(StringUtils.upperCaseFirstLetter(fields[i]));
            }
            return sb.toString();
        }


}
