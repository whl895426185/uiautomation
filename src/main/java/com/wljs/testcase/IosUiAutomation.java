package com.wljs.testcase;


public class IosUiAutomation {

    //引导页
    public static final String pageXpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypePageIndicator[1]";

    //立即体验
    public static final String experienceXpath = "type == 'XCUIElementTypeStaticText' AND label == '立即体验'";
    //我的
    public static final String mineXpath = "type == 'XCUIElementTypeButton' AND label == '我的'";
    //开发环境
    public static final String devEnXpath = "type == 'XCUIElementTypeButton' AND label == '开发环境'";
    //请输入手机号
    public static final String phoneCodeXpath = "type == 'XCUIElementTypeTextField' AND value =='请输入手机号'";
    //请输入验证码
    public static final String verificateCodeXpath = "type == 'XCUIElementTypeTextField' AND value == '请输入验证码'";
    //收起键盘
    public static final String doneXpath = "type == 'XCUIElementTypeButton' AND label == 'Done'";
    //登陆
    public static final String loginXpath = "type == 'XCUIElementTypeStaticText' AND label == '登录'";
    //请输入邀请码
    public static final String inviteCodeXpath = "type == 'XCUIElementTypeStaticText' AND label == '请输入邀请码'";
    //请输入微信号码
    public static final String weixinCodeXpath = "type == 'XCUIElementTypeTextField' AND value='请输入微信号码'";
    //将微信号展示给邀请人
    public static final String showWeiXinXpath = "type == 'XCUIElementTypeButton' AND label == 'ic product pay unselected'";
    //同意协议并注册
    public static final String passXpath = "type == 'XCUIElementTypeStaticText' AND label == '同意协议并注册'";
    //列表商品名称
    public static final String productNameForListXpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeCollectionView[1]/XCUIElementTypeCell[4]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]";
    //列表商品价格
    public static final String productPriceForListXpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeCollectionView[1]/XCUIElementTypeCell[4]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]";
    //详情商品名称
    public static final String productNameForDetailXpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]";
    //详情商品价格
    public static final String productPriceForDetailXpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[1]";


}
