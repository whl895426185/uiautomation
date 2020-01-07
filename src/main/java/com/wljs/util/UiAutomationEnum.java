package com.wljs.util;

import com.wljs.testcase.AndroidUiAutomation;
import com.wljs.testcase.IosUiAutomation;

public class UiAutomationEnum {
    /**
     * 事件枚举类
     */
    public enum EventEnum {
        LEFT("wipe-left", "向左滑动"),
        RIGHT("wipe-right", "向右滑动"),
        UP("wipe-up", "向上滑动"),
        DOWN("wipe-down", "向下滑动"),
        CLICK("click", "点击"),
        VIEW("view", "显示文本"),
        VIEWCLICK("view-click", "点击文本"),
        KILL("kill", "杀进程"),
        STARTUP("start-up", "启动App");

        private String event;
        private String name;

        private EventEnum(String event, String name) {
            this.event = event;
            this.name = name;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 类型枚举类
     */
    public enum TypeEnum {
        PAGE("page", "页面"),
        BUTTON("button", "按钮"),
        INPUT("input", "输入框"),
        LIST("list", "列表"),
        DETAIL("detail", "详情"),
        APP("app", "应用app");

        private String type;
        private String name;

        private TypeEnum(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 安卓设备用例枚举类
     */
    public enum AndroidAutomationEnum {
        UA000001("立即体验", AndroidUiAutomation.experienceXpath),
        UA000002("我的", AndroidUiAutomation.mineXpath),
        UA000003("测试", AndroidUiAutomation.devEnXpath),
        UA000004("请输入账号绑定的手机号", AndroidUiAutomation.phoneCodeXpath),
        UA000005("请输入验证码", AndroidUiAutomation.verificateCodeXpath),
        UA000007("登录", AndroidUiAutomation.loginXpath),
        UA000008("请输入邀请码", AndroidUiAutomation.inviteCodeXpath),
        UA000009("请输入微信号码", AndroidUiAutomation.weixinCodeXpath),
        UA0000010("将微信号展示给邀请人", AndroidUiAutomation.showWeiXinXpath),
        UA0000011("同意协议并注册", AndroidUiAutomation.passXpath),
        UA0000012("商品名称-list", AndroidUiAutomation.productNameForListXpath),
        UA0000013("商品价格-list", AndroidUiAutomation.productPriceForListXpath),
        UA0000014("商品名称-detail", AndroidUiAutomation.productNameForDetailXpath),
        UA0000015("商品价格-detail", AndroidUiAutomation.productPriceForDetailXpath);

        private String name;
        private String androidElement;


        private AndroidAutomationEnum(String name, String androidElement) {
            this.name = name;
            this.androidElement = androidElement;
        }

        public static String getAndroidElement(String name) {
            for (AndroidAutomationEnum str : AndroidAutomationEnum.values()) {
                if (str.getName().equals(name)) {
                    return str.getAndroidElement();
                }
            }
            return null;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAndroidElement() {
            return androidElement;
        }

        public void setAndroidElement(String androidElement) {
            this.androidElement = androidElement;
        }
    }

    /**
     * iOS设备用例枚举类
     */
    public enum IosAutomationEnum {
        UA000001("立即体验", IosUiAutomation.experienceXpath),
        UA000002("我的", IosUiAutomation.mineXpath),
        UA000003("开发环境", IosUiAutomation.devEnXpath),
        UA000004("请输入手机号", IosUiAutomation.phoneCodeXpath),
        UA000005("请输入验证码", IosUiAutomation.verificateCodeXpath),
        UA000006("完成", IosUiAutomation.doneXpath),
        UA000007("登录", IosUiAutomation.loginXpath),
        UA000008("请输入邀请码", IosUiAutomation.inviteCodeXpath),
        UA000009("请输入微信号码", IosUiAutomation.weixinCodeXpath),
        UA0000010("将微信号展示给邀请人", IosUiAutomation.showWeiXinXpath),
        UA0000011("同意协议并注册", IosUiAutomation.passXpath),
        UA0000012("商品名称-list", IosUiAutomation.productNameForListXpath),
        UA0000013("商品价格-list", IosUiAutomation.productPriceForListXpath),
        UA0000014("商品名称-detail", IosUiAutomation.productNameForDetailXpath),
        UA0000015("商品价格-detail", IosUiAutomation.productPriceForDetailXpath);

        private String name;
        private String iosElement;


        private IosAutomationEnum(String name, String iosElement) {
            this.name = name;
            this.iosElement = iosElement;
        }

        public static String getIosElement(String name) {
            for (IosAutomationEnum str : IosAutomationEnum.values()) {
                if (str.getName().equals(name)) {
                    return str.getIosElement();
                }
            }
            return null;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getIosElement() {
            return iosElement;
        }

        public void setIosElement(String iosElement) {
            this.iosElement = iosElement;
        }
    }

}
