package com.wljs.app;


import com.wljs.pojo.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 线程并发执行
 */
public class ExecuteTestng implements Callable {
    private Logger logger = LoggerFactory.getLogger(ExecuteTestng.class);

    private ArrayBlockingQueue queue;

    public ExecuteTestng(ArrayBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public ResponseData call() throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            String filename = String.valueOf(queue.take());

            TestNG testNG = new TestNG();
            List<String> suites = new ArrayList<String>();
            suites.add(filename);
            testNG.setTestSuites(suites);
            testNG.run();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            return responseData;
        }
    }




}
