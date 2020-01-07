package com.wljs.driver;

import com.wljs.pojo.StfDevicesFields;
import com.wljs.util.CommandUtil;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class InitIosDriver {
    private CommandUtil commandUtil = new CommandUtil();

    public IOSDriver initDriver(StfDevicesFields fields) {
        IOSDriver driver = null;
        try {
            //检查端口是否被占用,如果被占用先停掉
            commandUtil.stopProcess(fields);

            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(MobileCapabilityType.UDID, fields.getSerial());
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, fields.getDeviceName());
            cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, fields.getVersion());
            cap.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.sibu.futurebazaar");
            cap.setCapability(MobileCapabilityType.NO_RESET, true);
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            cap.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
            cap.setCapability("useNewWDA", true);
//            cap.setCapability("showXcodeLog", true);//显示用于运行测试的Xcode命令的输出
//            cap.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, fields.getWdaLocalPort());//自定义wda端口
////            cap.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, false);//设置不强制卸载Wda应用程序，不用为每个会话应用不同的启动项
//////            cap.setCapability(IOSMobileCapabilityType.START_IWDP, true);//开启ios-webkit-debug-proxy工具
//            cap.setCapability(IOSMobileCapabilityType.USE_PREBUILT_WDA, true);//跳过运行WDA应用程序的构建阶段
//            cap.setCapability("useSimpleBuildTest",true);//建立具有build-for-testing与运行测试test-without-building
//            cap.setCapability("wdaStartupRetryInterval", 20000);
////            cap.setCapability("useXctestrunFile",false);
            String path = "http://127.0.0.1:" + fields.getAppiumServerPort() + "/wd/hub";

            driver = new IOSDriver<IOSElement>(new URL(path), cap);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            return driver;
        }

    }
}
