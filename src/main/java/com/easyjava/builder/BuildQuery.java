package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import com.sun.org.apache.bcel.internal.Const;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BuildQuery {
    private static final Logger logger = LoggerFactory.getLogger(BuildQuery.class);

    public static void execute(TableInfo tableInfo){
        File folder = new File(Constants.PATH_QUERY);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;

        File poFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_QUERY + ";");
            bw.newLine();
            bw.newLine();

            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }

            if(tableInfo.getHaveDate() || tableInfo.getHaveDateTime()){
                bw.write("import java.util.Date;");
                bw.newLine();
            }

            bw.newLine();
            bw.newLine();

            //structure class note
            BuildComment.createClassComment(bw, tableInfo.getComment() +" search object");
            bw.write("public class " + className + " {");
            bw.newLine();

            List<FieldInfo> extendList = new ArrayList<FieldInfo>();
            for (FieldInfo field : tableInfo.getFieldList()) {

                BuildComment.createFieldComment(bw, field.getComment());
                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";\n\n");


                //String Param
                if(ArrayUtils.contains(Constants.SQL_STRING_TYPE, field.getSqlType())){
                    String propertyName = field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY;
                    bw.write("\tprivate " + field.getJavaType() + " " +propertyName +";");
                    bw.newLine();
                    bw.newLine();


                    FieldInfo fuzzyField = new FieldInfo();
                    fuzzyField.setJavaType(field.getJavaType());
                    fuzzyField.setPropertyName(propertyName);
                    extendList.add(fuzzyField);
                }

                if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, field.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, field.getSqlType())){
                    bw.write("\tprivate String " +field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START + ";");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tprivate String " +field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END + "; ");
                    bw.newLine();
                    bw.newLine();

                    FieldInfo timeStartField = new FieldInfo();
                    timeStartField.setJavaType("String");
                    timeStartField.setPropertyName(field.getPropertyName() +Constants.SUFFIX_BEAN_QUERY_TIME_START);
                    extendList.add(timeStartField);

                    FieldInfo timeEndField = new FieldInfo();
                    timeEndField.setJavaType("String");
                    timeEndField.setPropertyName(field.getPropertyName() +Constants.SUFFIX_BEAN_QUERY_TIME_END);
                    extendList.add(timeEndField);
                }
            }

            List<FieldInfo> fieldInfoList = tableInfo.getFieldList();
            fieldInfoList.addAll(extendList);

            for (FieldInfo field : tableInfo.getFieldList()) {
                String tempField = StringUtils.upperCaseFirstLetter(field.getPropertyName());
                bw.write("\tpublic void set" + tempField + "(" + field.getJavaType() + " " + field.getPropertyName() + ")  {");

                bw.newLine();
                bw.write("\t\tthis." + field.getPropertyName() + " = " + field.getPropertyName() + ";\n");
                bw.write("\t }");
                bw.newLine();
                bw.newLine();


                bw.write("\tpublic " + field.getJavaType() + " get" + tempField + "()  {");
                bw.newLine();
                bw.write("\t\treturn this." + field.getPropertyName() + ";\n");
                bw.write("\t }");
                bw.newLine();
                bw.newLine();
            }

              bw.write("}");
              bw.flush();
        }catch (Exception e){
            logger.error("创建po失败", e);
        }finally {
            if(bw!=null){
                try{
                    bw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(outw!=null){
                try{
                    outw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try{
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
