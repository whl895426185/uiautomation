package com.wljs.util;

import com.wljs.pojo.MoveCoordinates;
import com.wljs.pojo.ResponseData;
import com.wljs.pojo.StfDevicesFields;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementUtil {

    private Logger logger = LoggerFactory.getLogger(ElementUtil.class);

    private AndroidDriver androidDriver;
    private IOSDriver iosDriver;
    private StfDevicesFields fields;

    public ElementUtil(IOSDriver iosDriver, StfDevicesFields fields) {
        this.iosDriver = iosDriver;
        this.fields = fields;
    }

    public ElementUtil(AndroidDriver androidDriver, StfDevicesFields fields) {
        this.androidDriver = androidDriver;
        this.fields = fields;
    }

    /**
     * 计算滑动坐标点
     *
     * @param dimension
     * @param type      1向左滑动 2向上滑动
     * @return
     */
    public MoveCoordinates getMoveCoordinates(Dimension dimension, int type) {

        int width = dimension.width;
        int height = dimension.height;

        MoveCoordinates coordinates = new MoveCoordinates();

        if (1 == type) {//向左滑动
            coordinates.setOrginWith((new Double(width * 0.9)).intValue());
            coordinates.setOrginHeight(height / 2);
            coordinates.setMoveWidth((new Double(width * 0.15)).intValue());
            coordinates.setMoveHeight(height / 2);
        }
        if (2 == type) {//向上滑动
            coordinates.setOrginWith(width / 2);
            coordinates.setOrginHeight((new Double(height * 0.9)).intValue());
            coordinates.setMoveWidth(width / 2);
            coordinates.setMoveHeight(new Double(height * 0.1).intValue());
        }
        return coordinates;

    }

    /**
     * 显示等待元素出现(IOS特有)
     * <p>
     * xpath定位方式在 xcui 底层原生不支持，由 appium 额外支持的，定位速度很慢，而且有时候定位不到元素的情况存在。
     * 综上所述，在iOS的UI自动化中，使用原生支持的iOSNsPredicateString定位方式是最好，支持也是最好的
     * <p>
     * 仅支持 iOS 10或以上，可支持元素的单个属性和多个属性定位，推荐使用
     *
     * @param xpath
     * @return
     */
    public ResponseData iOSNsPredicateString(String xpath) {
        ResponseData responseData = new ResponseData();
        try {

            logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 元素xpath = 【" + xpath + "】");

            WebDriverWait wait = new WebDriverWait(iosDriver, 20);

            By by = MobileBy.iOSNsPredicateString(xpath);

            WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));

            responseData.setWebElement(webElement);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 没有发现元素【" + xpath + "】");

            responseData = new ResponseData(false, e, "没有获取到元素：" + xpath);

        } finally {
            responseData.setFields(fields);
            return responseData;
        }
    }


    /**
     * 显示等待元素出现
     * <p>
     * 根据xpath直接定位
     *
     * @param driver
     * @param xpath
     * @return
     */
    public ResponseData isAppearByXpath(IOSDriver driver, String xpath) {

        return waitElement(driver, xpath);

    }

    private ResponseData waitElement(WebDriver driver, String xpath) {
        logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 元素xpath = 【" + xpath + "】");
        ResponseData responseData = new ResponseData();
        try {

            WebDriverWait wait = new WebDriverWait(driver, 3);


            By by = MobileBy.xpath(xpath);

            WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));

            responseData.setWebElement(webElement);

        } catch (Exception e) {
            //e.printStackTrace();
            logger.info(":::::::::::::::::<<<" + fields.getDeviceName() + ">>>::::::::::::::::: 没有发现元素【" + xpath + "】");

            responseData = new ResponseData(false, e, "没有获取到元素：" + xpath);

        } finally {
            responseData.setFields(fields);
            return responseData;
        }
    }

    /**
     * 显示等待元素出现
     * <p>
     * 根据xpath直接定位
     *
     * @param driver
     * @param xpath
     * @return
     */
    public ResponseData isAppearByXpath(AndroidDriver driver, String xpath) {
        return waitElement(driver, xpath);

    }


}
