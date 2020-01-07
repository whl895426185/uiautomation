package com.wljs.testcase;

import com.wljs.pojo.ResponseData;
import com.wljs.pojo.StfDevicesFields;
import com.wljs.pojo.UiAutomation;
import com.wljs.service.StfDevicesService;
import com.wljs.testcase.unitcase.Initialization;
import com.wljs.util.ExcelUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class RegressionTesting {
    private Logger logger = LoggerFactory.getLogger(RegressionTesting.class);

    //初始化driver
    private AndroidDriver androidDriver;
    private IOSDriver iosDriver;

    //设备信息
    private StfDevicesFields fields;

    private Initialization initialization;

    //测试用例集合
    private List<UiAutomation> list;
    private ExcelUtil excelUtil = new ExcelUtil();

    //安卓测试用例
    private String android_testcase_url = "/Volumes/SoureCode/testcase/android/";
    private AndroidExeuteTestCase androidExeuteTestCase = new AndroidExeuteTestCase();

    //ios测试用例
    private String ios_testcase_url = "/Volumes/SoureCode/testcase/ios/";
    private IosExeuteTestCase iosExeuteTestCase = new IosExeuteTestCase();

    //判断当前执行的设备是安卓还是iOS
    private boolean platformFlag = false;//IOs:true, android: false

    //响应结果
    private ResponseData responseData = new ResponseData();


    @BeforeClass
    @Parameters({"manufacturer", "model", "serial", "version", "deviceName", "platform", "appiumServerPort", "systemPort", "wdaLocalPort", "index"})
    public void initialization(String manufacturer, String model, String serial, String version, String deviceName, String platform, String appiumServerPort, String systemPort, String wdaLocalPort, String index) {
        try {
            //传参参数对象
            fields = new StfDevicesFields();
            fields.setManufacturer(manufacturer);
            fields.setModel(model);
            fields.setSerial(serial);
            fields.setVersion(version);
            fields.setDeviceName(deviceName);
            fields.setPlatform(platform);
            fields.setAppiumServerPort(Integer.valueOf(appiumServerPort));
            fields.setSystemPort(Integer.valueOf(systemPort));
            fields.setWdaLocalPort(Integer.valueOf(wdaLocalPort));
            fields.setIndex(Integer.valueOf(index));

            initialization = new Initialization(fields);

            //启动appium服务
            initialization.startAppiumService();

            //初始化driver,启动app
            initialization.startDevice();

            androidDriver = initialization.getAndroidDriver();
            iosDriver = initialization.getIosDriver();

            if (platform.equals("Android")) {
                platformFlag = false;

            } else {
                platformFlag = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test(description = "滑动引导页，登陆App首页", priority = 1, enabled = false)
    public void bootPageApp() throws Exception {
        logger.info(":::::::::::::::::<<<执行<<滑动引导页，登陆App首页>>测试用例>>>:::::::::::::::::");
        chooseTestCase("page.xlsx");

    }

    @Test(description = "登陆App", priority = 2)
    public void loginApp() throws Exception {
        logger.info(":::::::::::::::::<<<执行<<登陆App>>测试用例>>>:::::::::::::::::");
        chooseTestCase("login.xlsx");
    }

    @Test(description = "查看商品", priority = 3, enabled = false)
    public void viewProduct() throws Exception {
        logger.info(":::::::::::::::::<<<执行<<登陆App>>测试用例>>>:::::::::::::::::");
        chooseTestCase("product.xlsx");
    }

    public void chooseTestCase(String fileName) throws Exception {
        if (platformFlag) {
            list = excelUtil.readExcel(ios_testcase_url + fileName);
            responseData = iosExeuteTestCase.exeuteTestCase(iosDriver, fields, list);
        } else {
            list = excelUtil.readExcel(android_testcase_url + fileName);
            responseData = androidExeuteTestCase.exeuteTestCase(androidDriver, fields, list);
        }

        if (null != responseData) {
            androidDriver = responseData.getAndroidDriver();
            iosDriver = responseData.getIosDriver();
            if (!responseData.isStatus()) {

                throw new Exception(responseData.getExMsg());
            }
        }
    }

    @AfterClass
    public void endExecutionCase() {
        try {
            Thread.sleep(20000);
            initialization.stopDevice(androidDriver, iosDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //释放设备资源
//        StfDevicesService devicesService = new StfDevicesService();
//        devicesService.releaseResources(fields.getSerial());
    }

}
