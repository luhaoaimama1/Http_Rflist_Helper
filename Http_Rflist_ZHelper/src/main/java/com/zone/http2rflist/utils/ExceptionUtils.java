package com.zone.http2rflist.utils;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/24.
 */
public class ExceptionUtils {
    public static void quiet(Exception e){
        LogUtils.e(callMethodAndLine()+"\t Message:"+e.getMessage()+" \t cause:"+e.getCause());
    }

    /**
     * log This method can display the hyperlink
     */
    private static String callMethodAndLine() {
        String result = " at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result += thisMethodStack.getClassName()+ ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
}
