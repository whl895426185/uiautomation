package com.wljs.testcase;

import com.wljs.pojo.MoveCoordinates;
import com.wljs.pojo.ResponseData;
import com.wljs.pojo.StfDevicesFields;
import com.wljs.pojo.UiAutomation;
import com.wljs.testcase.unitcase.Initialization;
import com.wljs.util.CommandUtil;
import com.wljs.util.ElementUtil;
import com.wljs.util.UiAutomationEnum;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class AndroidExeuteTestCase {
    private Logger logger = LoggerFactory.getLogger(AndroidExeuteTestCase.class);
    //获取元素
    private ElementUtil elementUtil;
    //返回响应对象
    private ResponseData responseData = new ResponseData();

    public ResponseData exeuteTestCase(AndroidDriver driver, StfDevicesFields fields, List<UiAutomation> list) {
        elementUtil = new ElementUtil(driver, fields);

        //获取屏幕的大小
        Dimension dimension = driver.manage().window().getSize();

        int index = 0;
        for (UiAutomation automation : list) {
            String event = automation.getEvent();//事件
            String type = automation.getType();//类型
            String name = automation.getName();//名称
            String value = automation.getValue();//参数

            //处理弹框
            if (index == 0) {
                elasticFrame(driver, fields);
            }

            //向左滑动页面
            if (event.equals(UiAutomationEnum.EventEnum.LEFT.getEvent()) && type.equals(UiAutomationEnum.TypeEnum.PAGE.getType())) {

                String key = null;
                if (automation.getValue().equals("1")) {
                    key = AndroidUiAutomation.pageOneXpath;
                } else if (automation.getValue().equals("2")) {
                    key = AndroidUiAutomation.pageTwoXpath;
                }

                responseData = elementUtil.isAppearByXpath(driver, key);
                if (responseData.isStatus()) {

                    MoveCoordinates moveCoordinates = elementUtil.getMoveCoordinates(dimension, 1);

                    new IOSTouchAction(driver).press(PointOption.point(moveCoordinates.getOrginWith(), moveCoordinates.getOrginHeight())).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                            .moveTo(PointOption.point(moveCoordinates.getMoveWidth(), moveCoordinates.getMoveHeight())).release().perform();
                }
            }

            //向上滑动页面
            if (event.equals(UiAutomationEnum.EventEnum.UP.getEvent()) && type.equals(UiAutomationEnum.TypeEnum.PAGE.getType())) {
                MoveCoordinates moveCoordinates = elementUtil.getMoveCoordinates(dimension, 2);

                new IOSTouchAction(driver).press(PointOption.point(moveCoordinates.getOrginWith(), moveCoordinates.getOrginHeight())).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                        .moveTo(PointOption.point(moveCoordinates.getMoveWidth(), moveCoordinates.getMoveHeight())).release().perform();
            }

            //点击按钮
            if (event.equals(UiAutomationEnum.EventEnum.CLICK.getEvent()) && type.equals(UiAutomationEnum.TypeEnum.BUTTON.getType())) {
                responseData = elementUtil.isAppearByXpath(driver, UiAutomationEnum.AndroidAutomationEnum.getAndroidElement(name));
                if (responseData.isStatus()) {
                    responseData.getWebElement().click();
                    //logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 模拟点击【" + automation.getName() + "】按钮");
                }
            }

            //点击输入框
            if (event.equals(UiAutomationEnum.EventEnum.CLICK.getEvent()) && type.equals(UiAutomationEnum.TypeEnum.INPUT.getType())) {

                responseData = elementUtil.isAppearByXpath(driver, UiAutomationEnum.AndroidAutomationEnum.getAndroidElement(name));
                if (responseData.isStatus()) {
                    responseData.getWebElement().click();
                    if (null != value) {
                        String remark = "";
                        for (int i = 0; i < value.length(); i++) {
                            String str = String.valueOf(value.charAt(i));
                            driver.pressKey(new KeyEvent(getKey(str)));

                            remark += str;

                        }
                        logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 模拟输入 = " + remark);
                    }
                }

            }

            //显示文本
            if (event.equals(UiAutomationEnum.EventEnum.VIEW.getEvent())) {
                if ((type.equals(UiAutomationEnum.TypeEnum.LIST.getType()) || type.equals(UiAutomationEnum.TypeEnum.DETAIL.getType()))) {

                    String key = automation.getName() + "-" + type;
                    responseData = elementUtil.isAppearByXpath(driver, UiAutomationEnum.AndroidAutomationEnum.getAndroidElement(key));
                    if (responseData.isStatus()) {
                        logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 显示" + name + " = " + responseData.getWebElement().getText());
                    }

                }
            }

            //点击文本
            if (event.equals(UiAutomationEnum.EventEnum.VIEWCLICK.getEvent())) {
                String key = automation.getName() + "-" + type;
                responseData = elementUtil.isAppearByXpath(driver, UiAutomationEnum.AndroidAutomationEnum.getAndroidElement(key));
                if (responseData.isStatus()) {
                    WebElement element = responseData.getWebElement();
                    element.click();
                }
            }

            //杀进程
            if (event.equals(UiAutomationEnum.EventEnum.KILL.getEvent())) {
                killProcess(fields);
            }

            //启动app
            if (event.equals(UiAutomationEnum.EventEnum.STARTUP.getEvent()) && type.equals(UiAutomationEnum.TypeEnum.APP.getType())) {

                Initialization initialization = new Initialization(fields);

                //初始化driver,启动app
                try {
                    initialization.startDevice();

                    //启动完后暂停2分钟，避免没有加载完
                    Thread.sleep(20000);
                    driver = initialization.getAndroidDriver();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            index++;
            if (null != responseData) {
                if (!responseData.isStatus()) {
                    break;
                }
            }
        }

        responseData.setAndroidDriver(driver);
        return responseData;
    }

    /**
     * 点击弹框
     */
    public boolean elasticFrame(AndroidDriver driver, StfDevicesFields fields) {
        try {
            //显示等待5秒
            WebDriverWait wait = new WebDriverWait(driver, 5);
            By by = By.xpath("//*//*[contains(@text,'允许')]");

            wait.until(ExpectedConditions.presenceOfElementLocated(by));

            int alterAcceptCount = 2;//弹框次数

            //点击【允许】按钮
            if (fields.getDeviceName().contains("OPPO")
                    || fields.getDeviceName().contains("MEIZU")) {
                alterAcceptCount = 1;
            }

            for (int i = 0; i < alterAcceptCount; i++) {
                driver.switchTo().alert().accept();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            return true;
        }
    }

    /**
     * 获取安卓控件key
     *
     * @param numStr
     * @return
     */
    private AndroidKey getKey(String numStr) {
        int result = Integer.valueOf(numStr);
        if (0 == result) {
            return AndroidKey.DIGIT_0;
        } else if (1 == result) {
            return AndroidKey.DIGIT_1;
        } else if (2 == result) {
            return AndroidKey.DIGIT_2;
        } else if (3 == result) {
            return AndroidKey.DIGIT_3;
        } else if (4 == result) {
            return AndroidKey.DIGIT_4;
        } else if (5 == result) {
            return AndroidKey.DIGIT_5;
        } else if (6 == result) {
            return AndroidKey.DIGIT_6;
        } else if (7 == result) {
            return AndroidKey.DIGIT_7;
        } else if (8 == result) {
            return AndroidKey.DIGIT_8;
        } else if (9 == result) {
            return AndroidKey.DIGIT_9;
        }
        return null;
    }

    /**
     * 杀进程
     */
    private ResponseData killProcess(StfDevicesFields fields) {

        //命令执行工具类
        CommandUtil commandUtil = new CommandUtil();

        String killCommand = "adb -s " + fields.getSerial() + " shell am force-stop com.sibu.futurebazaar";

        logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 切换环境，执行ADB命令杀掉APP进程： " + killCommand);

        String killProcess = commandUtil.getProcess(killCommand, fields);

        if (null == killProcess || ("").equals(killProcess)) {
            //查看进程是否还存在
            String command = "adb -s " + fields.getSerial() + " shell ps|grep com.sibu.futurebazaar";
            String process = commandUtil.getProcess(command, fields);

            if (null == process || ("").equals(process)) {
                logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: APP进程已杀");
            }
        }

        return new ResponseData();


    }
}
