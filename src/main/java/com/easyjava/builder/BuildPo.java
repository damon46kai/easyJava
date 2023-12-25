package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class BuildPo {
    public static final Logger logger = LoggerFactory.getLogger(BuildPo.class);
    public static void execute(TableInfo tableInfo){
        File folder = new File(Constants.PATH_PO);
        if(!folder.exists()){
            folder.mkdirs();
        }

        File poFile = new File(folder, tableInfo.getBeanName()+ ".java");
//        try{
//            file.createNewFile();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        OutputStream out = null;

        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);
            bw.write("package "+ Constants.PACKAGE_PO+";");
            bw.newLine();
            bw.newLine();
            bw.write("import java.io.Serializable;");
            bw.newLine();

            if(tableInfo.getHaveDate() || tableInfo.getHaveDateTime()){
                bw.write("import java.util.Date;\n ");
            }
            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
            }


            bw.newLine();
            bw.newLine();

            //构建类
            BuildComment.createClassComment(bw, tableInfo.getComment());
            bw.write("public class " +tableInfo.getBeanName() +" implements Serializable {");
            bw.newLine();
            bw.newLine();

            for(FieldInfo field: tableInfo.getFieldList()){
                BuildComment.createFieldComment(bw, field.getComment());
                bw.write("\tprivate "+ field.getJavaType() + " "  + field.getPropertyName() + ";\n\n");

            }

            bw.write("}");
            bw.flush();
        }catch (Exception e){

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
