package com.wljs.testcase;

import com.wljs.pojo.MoveCoordinates;
import com.wljs.pojo.ResponseData;
import com.wljs.pojo.StfDevicesFields;
import com.wljs.pojo.UiAutomation;
import com.wljs.util.*;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class IosExeuteTestCase {
    private Logger logger = LoggerFactory.getLogger(IosExeuteTestCase.class);
    //获取元素
    private ElementUtil elementUtil;
    //返回响应对象
    private ResponseData responseData = new ResponseData();

    public ResponseData exeuteTestCase(IOSDriver driver, StfDevicesFields fields, List<UiAutomation> list) {
        elementUtil = new ElementUtil(driver, fields);

        //获取屏幕的大小
        Dimension dimension = driver.manage().window().getSize();

        for (UiAutomation automation : list) {

            //向左滑动页面
            if (automation.getEvent().equals(UiAutomationEnum.EventEnum.LEFT.getEvent())
                    && automation.getType().equals(UiAutomationEnum.TypeEnum.PAGE.getType())) {
                responseData = elementUtil.isAppearByXpath(driver, IosUiAutomation.pageXpath);
                if (responseData.isStatus()) {
                    MoveCoordinates moveCoordinates = elementUtil.getMoveCoordinates(dimension, 1);

                    new IOSTouchAction(driver).press(PointOption.point(moveCoordinates.getOrginWith(), moveCoordinates.getOrginHeight())).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                            .moveTo(PointOption.point(moveCoordinates.getMoveWidth(), moveCoordinates.getMoveHeight())).release().perform();

                    logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 模拟向左滑动页面");
                }
            }

            //向上滑动页面
            if (automation.getEvent().equals(UiAutomationEnum.EventEnum.UP.getEvent())
                    && automation.getType().equals(UiAutomationEnum.TypeEnum.PAGE.getType())) {

                MoveCoordinates moveCoordinates = elementUtil.getMoveCoordinates(dimension, 2);

                new IOSTouchAction(driver).press(PointOption.point(moveCoordinates.getOrginWith(), moveCoordinates.getOrginHeight())).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                        .moveTo(PointOption.point(moveCoordinates.getMoveWidth(), moveCoordinates.getMoveHeight())).release().perform();

                logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 模拟向上滑动页面");
            }

            //点击按钮
            if (automation.getEvent().equals(UiAutomationEnum.EventEnum.CLICK.getEvent())
                    && automation.getType().equals(UiAutomationEnum.TypeEnum.BUTTON.getType())) {
                responseData = elementUtil.iOSNsPredicateString(UiAutomationEnum.IosAutomationEnum.getIosElement(automation.getName()));
                if (responseData.isStatus()) {
                    responseData.getWebElement().click();
                    logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 模拟点击【" + automation.getName() + "】按钮");
                }
            }

            //点击输入框
            if (automation.getEvent().equals(UiAutomationEnum.EventEnum.CLICK.getEvent())
                    && automation.getType().equals(UiAutomationEnum.TypeEnum.INPUT.getType())) {

                responseData = elementUtil.iOSNsPredicateString(UiAutomationEnum.IosAutomationEnum.getIosElement(automation.getName()));
                if (responseData.isStatus()) {
                    responseData.getWebElement().click();
                    logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 模拟点击【" + automation.getName() + "】按钮");

                    responseData.getWebElement().sendKeys(automation.getValue());
                    logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 模拟输入 = " + automation.getValue());
                }
            }

            //显示文本
            if (automation.getEvent().equals(UiAutomationEnum.EventEnum.VIEW.getEvent())) {
                if ((automation.getType().equals(UiAutomationEnum.TypeEnum.LIST.getType())
                        || automation.getType().equals(UiAutomationEnum.TypeEnum.DETAIL.getType()))) {

                    String key = automation.getName() + "-" + automation.getType();
                    responseData = elementUtil.isAppearByXpath(driver, UiAutomationEnum.IosAutomationEnum.getIosElement(key));
                    if (responseData.isStatus()) {
                        logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 显示" + automation.getName() + " = " + responseData.getWebElement().getText());
                    }

                }
            }

            //点击文本
            if (automation.getEvent().equals(UiAutomationEnum.EventEnum.VIEWCLICK.getEvent())) {
                String key = automation.getName() + "-" + automation.getType();
                responseData = elementUtil.isAppearByXpath(driver, UiAutomationEnum.IosAutomationEnum.getIosElement(key));
                if (responseData.isStatus()) {
                    WebElement element = responseData.getWebElement();
                    element.click();
                }
            }

            if (null != responseData) {
                if (!responseData.isStatus()) {
                    break;
                }
            }
        }

        responseData.setIosDriver(driver);

        return responseData;
    }
}
