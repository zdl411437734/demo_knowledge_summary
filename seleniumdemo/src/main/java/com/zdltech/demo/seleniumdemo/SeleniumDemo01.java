package com.zdltech.demo.seleniumdemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Selenium Demo 01 第一个Demo
 *  * chromedriver 下载
 *  * https://npm.taobao.org/mirrors/chromedriver/
 *  https://chromedriver.storage.googleapis.com/index.html?
 * Chrome：https://sites.google.com/a/chromium.org/chromedriver/downloads
 * Firefox：https://github.com/mozilla/geckodriver/releases
 * Edge：https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
 * Safari：https://webkit.org/blog/6900/webdriver-support-in-safari-10/
 *
 * https://blog.csdn.net/weixin_42635252/article/details/90755468
 */
public class SeleniumDemo01 {
    public static void main(String[] args) {
        //chromedriver位置
        String chromeriverPath = "/Users/jason/Documents/opt/java_workspace/demo_knowledge_summary/files/chromedriver";
//        String chromeriverPath = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
        System.setProperty("webdriver.chrome.driver",chromeriverPath);
        //测试连接
        String url  = "https://www.baidu.com/";
        //启动webdriver
        WebDriver webDriver = new ChromeDriver();
        //浏览器跳转测试连接
        webDriver.get(url);
        //等待数秒
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印Html
        System.out.println(webDriver.getPageSource());
    }
}
