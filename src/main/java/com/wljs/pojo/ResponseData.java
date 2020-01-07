package com.wljs.pojo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;


public class ResponseData implements Serializable {

    private StfDevicesFields fields;//设备信息
    private WebElement webElement;//元素对象
    private boolean status;    //成功状态（true成功，false失败）
    private Exception exception;//异常对象
    private String imagePath;//异常截图
    private String exMsg;//自定义异常信息

    //关闭/退出app时用到
    private AndroidDriver androidDriver;
    private IOSDriver iosDriver;


    public ResponseData() {
        this.status = true;
        this.exception = null;
        this.fields = null;
        this.exMsg = null;
        this.imagePath = null;
        this.webElement = null;
    }

    public ResponseData(boolean status, Exception exception, String exMsg) {
        this.status = status;
        this.exception = exception;
        this.exMsg = exMsg;

    }

    public ResponseData(boolean status, Exception exception, String exMsg, String imagePath) {
        this.status = status;
        this.exception = exception;
        this.exMsg = exMsg;
        this.imagePath = imagePath;

    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public StfDevicesFields getFields() {
        return fields;
    }

    public void setFields(StfDevicesFields fields) {
        this.fields = fields;
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
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
}
