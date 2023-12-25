package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;

public class Constants {

    public static String AUTHOR_COMMENT ;
    public static Boolean IGNORE_TABLE_PREFIX;

    public static String SUFFIX_BEAN_PARAM;

    private static String PATH_JAVA = "java";
    private static String PATH_RESOURCES = "resources";

    public static String PATH_BASE ;

    public static String PACKAGE_BASE;

    public static String PATH_PO;

    public static String PACKAGE_PO;

    static {

        AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");
        PATH_BASE = PropertiesUtils.getString("path.base");

        PATH_BASE = PATH_BASE  + PATH_JAVA + "/" +PropertiesUtils.getString("package.base");
        PATH_BASE = PATH_BASE.replace(".","/");

        PATH_PO = PATH_BASE + "/" +PropertiesUtils.getString("package.po").replace(".","/");

        PACKAGE_BASE = PropertiesUtils.getString("package.base");

        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
    }

    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime","timestamp"};

    public final static String[] SQL_DATE_TYPES = new String[]{"date"};

    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};

    public final static String[] SQL_STRING_TYPE = new String[]{"char","varchar","text","mediumtext","longtext"};

    public final static String[] SQL_INTEGER_TYPE = new String[]{"int","tinyint"};

    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};


    public static void main(String[] args){
        System.out.println(PACKAGE_BASE);
    }
}
