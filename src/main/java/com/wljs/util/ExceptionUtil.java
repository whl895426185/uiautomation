package com.wljs.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtil {

    /**
     * 返回完整的异常信息
     *
     * @param e
     * @return
     */
    public String exceptionMsg(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        return exception;
    }


}
