package com.wljs.testcase.unitcase;

import com.wljs.driver.InitIosDriver;
import com.wljs.pojo.StfDevicesFields;
import com.wljs.driver.InitAndroidDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wljs.appium.AppiumService;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 初始化
 *
 * @since Isaac
 */
public class Initialization {

    private Logger logger = LoggerFactory.getLogger(Initialization.class);
    private AndroidDriver androidDriver;
    private IOSDriver iosDriver;
    private StfDevicesFields stfDevicesFields;

    //初始化driver工具类
    private InitAndroidDriver initAndroidDriver = new InitAndroidDriver();
    private InitIosDriver initIosDriver = new InitIosDriver();

    public Initialization(StfDevicesFields stfDevicesFields) {
        this.stfDevicesFields = stfDevicesFields;
    }


    /**
     * 启动appium服务
     */
    public void startAppiumService() {
        AppiumService appiumService = new AppiumService();
        appiumService.startAppiumServer(stfDevicesFields);
    }

    /**
     * 启动app
     *
     * @throws MalformedURLException
     */
    public void startDevice() throws MalformedURLException {
        //初始化driver
        if (stfDevicesFields.getPlatform().equals("Android")) {

            androidDriver = initAndroidDriver.initDriver(stfDevicesFields);

            setAndroidDriver(androidDriver);

        } else if (stfDevicesFields.getPlatform().equals("iOS")) {

            iosDriver = initIosDriver.initDriver(stfDevicesFields);

            setIosDriver(iosDriver);

        }
        logger.info(":::::::::::::::::<<<" + stfDevicesFields.getDeviceName() + ">>>::::::::::::::::: 启动未来集市APP");
    }

    /**
     * 关闭app,释放内存
     */
    public void stopDevice(AndroidDriver aDriver, IOSDriver iDriver) {

        if (stfDevicesFields.getPlatform().equals("Android")) {

            aDriver.closeApp();
            aDriver.quit();

        } else if (stfDevicesFields.getPlatform().equals("iOS")) {

            iDriver.closeApp();
            iDriver.quit();

        }

        logger.info(":::::::::::::::::<<<" + stfDevicesFields.getDeviceName() + ">>>::::::::::::::::: 关闭未来集市APP");
    }


    public void takeScreenShot() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String dateStr = sf.format(date);
        String path = this.getClass().getSimpleName() + "_" + dateStr + ".png";
        if (null != iosDriver) {
            takeScreenShot((TakesScreenshot) iosDriver, path);
        } else {
            takeScreenShot((TakesScreenshot) androidDriver, path);
        }

    }

    public void takeScreenShot(TakesScreenshot drivername, String path) {
        // this method will take screen shot ,require two parameters ,one is
        // driver name, another is file name
        String currentPath = System.getProperty("user.dir"); // get current work
        logger.info(currentPath);
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy
        try {
            logger.info("save snapshot path is:" + currentPath + path);
            FileUtils.copyFile(scrFile, new File(currentPath + "\\" + path));
        } catch (Exception e) {
            logger.error("Can't save screenshot");
            e.printStackTrace();
        } finally {
            logger.info("screen shot finished");
        }
    }


    public AndroidDriver getAndroidDriver() {
        return androidDriver;
    }

    public void setAndroidDriver(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
    }

    public IOSDriver getIosDriver() {
        return iosDriver;
    }

    public void setIosDriver(IOSDriver iosDriver) {
        this.iosDriver = iosDriver;
    }

    public StfDevicesFields getStfDevicesFields() {
        return stfDevicesFields;
    }

    public void setStfDevicesFields(StfDevicesFields stfDevicesFields) {
        this.stfDevicesFields = stfDevicesFields;
    }

}
