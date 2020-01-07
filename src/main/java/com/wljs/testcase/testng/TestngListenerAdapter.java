package com.wljs.testcase.testng;

import com.wljs.testcase.unitcase.Initialization;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListenerAdapter extends TestListenerAdapter {
    private Logger logger = LoggerFactory.getLogger(TestngListenerAdapter.class);

    @Override
    public void onTestSuccess(ITestResult tr) {
        logger.info("Test Success");
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        logger.error("Test Failure");
        super.onTestFailure(tr);
        takeScreenShot(tr);
    }

    private void takeScreenShot(ITestResult tr) {
//        Initialization b = tr.getInstance();
//
//        b.takeScreenShot();
    }


    @Override
    public void onTestSkipped(ITestResult tr) {
        logger.error("Test Skipped");
        super.onTestSkipped(tr);
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Finsh");
        super.onTestStart(result);
    }

    @Override
    public void onStart(ITestContext testContext) {
        logger.info("Test Start");
        super.onStart(testContext);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        logger.info("Test Finish");
        super.onFinish(testContext);
    }
}
