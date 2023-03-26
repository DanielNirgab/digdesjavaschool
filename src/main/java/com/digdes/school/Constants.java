package com.digdes.school;

import java.util.regex.Pattern;



public class Constants {
    public static final Pattern patternAllObj = Pattern.compile("(=|!=|>=|<=|>|<|\\b(SELECT|UPDATE|INSERT" +
            "|DELETE|VALUES|WHERE|LIKE|ILIKE|AND|OR|true|false)" +
            "\\b|\\d+\\.?\\d*|'[\\wА-я]+'|'%?[\\wА-я]+%?')", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternOperation = Pattern.compile("(=|!=|>=|<=|>|<|like|ilike)", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternLong = Pattern.compile("('id'|'age')", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternDouble = Pattern.compile("('cost')", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternBoolean = Pattern.compile("('active')", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternString = Pattern.compile("('lastName')", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternAndOr = Pattern.compile("(AND|OR)", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternLike = Pattern.compile("(LIKE|ILIKE)", Pattern.CASE_INSENSITIVE);

    public static final Pattern patternCommonRow = Pattern.compile("('id'|'age'|'lastName'|'cost'|'active')", Pattern.CASE_INSENSITIVE);
    public static final Pattern patternCommand = Pattern.compile("(INSERT|UPDATE|VALUES|DELETE|SELECT|WHERE)", Pattern.CASE_INSENSITIVE);
    public static final String COMMAND_INSERT = "INSERT";
    public static final String COMMAND_UPDATE = "UPDATE";
    public static final String COMMAND_VALUES = "VALUES";
    public static final String COMMAND_DELETE = "DELETE";
    public static final String COMMAND_SELECT = "SELECT";
    public static final String COMMAND_WHERE = "WHERE";

}
