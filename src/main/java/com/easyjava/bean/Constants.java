package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;

public class Constants {

    public static String AUTHOR_COMMENT ;
    public static Boolean IGNORE_TABLE_PREFIX;

    public static String SUFFIX_BEAN_QUERY;

    public static String SUFFIX_BEAN_QUERY_FUZZY;
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;

    //need ignore property
    public static String IGNORE_BEAN_TOJSON_FILED;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;
    public static String GETIGNORE_BEAN_TOJSON_CLASS;

    //date serialize unserialize
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_FORMAT_CLASS;

    public static String BEAN_DATE_UNFORMAT_EXPRESSION;
    public static String BEAN_DATE_UNFORMAT_CLASS;


    private static String PATH_JAVA = "java";
    private static String PATH_RESOURCES = "resources";



    public static String PACKAGE_BASE;

    public static String PACKAGE_QUERY;

    public static String PACKAGE_PO;

    public static String PACKAGE_UTILS;
    public static String PACKAGE_ENUMS;

    public static String PATH_PO;

    public static String PATH_QUERY;

    public static String PATH_BASE ;
    public static String PATH_UTILS;
    public static String PATH_ENUMS;


    static {

        //need ignore property
        IGNORE_BEAN_TOJSON_FILED = PropertiesUtils.getString("ignore.bean.tojson.filled");
        IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.tojson.expression");
        GETIGNORE_BEAN_TOJSON_CLASS = PropertiesUtils.getString("ignore.bean.tojson.class");

        //date serialize unserialize
        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
        BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");

        BEAN_DATE_UNFORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.unformat.expression");
        BEAN_DATE_UNFORMAT_CLASS = PropertiesUtils.getString("bean.date.unformat.class");

        AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");

        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtils.getString("suffix.bean.query.time.start");
        SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtils.getString("suffix.bean.query.time.end");

        PATH_BASE = PropertiesUtils.getString("path.base");

        PACKAGE_BASE = PropertiesUtils.getString("package.base");
        //PO
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getString("package.query");

        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.utils");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enums");


        PATH_BASE = PropertiesUtils.getString("path.base");
        PATH_BASE = PATH_BASE +PATH_JAVA;

        PATH_PO = PATH_BASE + "/" +PACKAGE_PO.replace(".","/");
        PATH_QUERY = PATH_BASE + "/" + PACKAGE_QUERY.replace(",", "/");
        PATH_UTILS = PATH_BASE + "/" + PACKAGE_UTILS.replace(",", "/");
        PATH_ENUMS = PATH_BASE + "/" + PACKAGE_ENUMS.replace(",", "/");
    }

    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime","timestamp"};

    public final static String[] SQL_DATE_TYPES = new String[]{"date"};

    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};

    public final static String[] SQL_STRING_TYPE = new String[]{"char","varchar","text","mediumtext","longtext"};

    public final static String[] SQL_INTEGER_TYPE = new String[]{"int","tinyint"};

    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};


    public static void main(String[] args){
        System.out.println(PACKAGE_PO);
        System.out.println(PATH_PO);
    }
}
