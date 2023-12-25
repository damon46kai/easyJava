package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.utils.DateUtils;

import java.io.BufferedWriter;
import java.util.Date;


public class BuildComment {
    public static void createClassComment(BufferedWriter bw, String classComment) throws Exception{
        bw.write("/**");
        bw.newLine();
        bw.write(" * @Description:"+classComment+"\n");
        bw.write(" * @Author: "+ Constants.AUTHOR_COMMENT +"\n");
        bw.write(" * @date: "+  DateUtils.format(new Date(), DateUtils._YYYMMDD)  +"\n */ \n");
    };

    public static void createFieldComment(BufferedWriter bw,String fieldComment) throws Exception{
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * " + fieldComment==null?"":fieldComment);

        bw.write(" \n\t */ \n");
    }

    public static void createMethodComment(){

    }
}
