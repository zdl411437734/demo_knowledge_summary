package com.zdltech.demo.seleniumdemo;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Selenium 获取Cookie
 * chromedriver 下载
 * https://npm.taobao.org/mirrors/chromedriver/
 * https://chromedriver.storage.googleapis.com/index.html?
 * Chrome：https://sites.google.com/a/chromium.org/chromedriver/downloads
 * Firefox：https://github.com/mozilla/geckodriver/releases
 * Edge：https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
 * Safari：https://webkit.org/blog/6900/webdriver-support-in-safari-10/
 */
public class SeleniumDemoCookies {
    public static void main(String[] args) {

        //浏览器安装路径
        //chromedriver位置
        String chromeriverPath = "/Users/jason/Documents/opt/java_workspace/demo_knowledge_summary/files/chromedriver";
        System.setProperty("webdriver.chrome.driver",chromeriverPath);
        //测试连接
        String url  = "https://piaoju.jd.com/";
        //启动webdriver
        WebDriver webDriver = new ChromeDriver();
        //浏览器跳转测试连接
        webDriver.get(url);
        //创建cookieFile

        try {
            File cookieFile=new File("piaoju.cookie.txt");
            if (cookieFile.exists()){
                cookieFile.delete();
            }
            cookieFile.createNewFile();
            FileWriter fileWriter = new FileWriter(cookieFile);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

            boolean isRun = true;
            String tmpUrl = null;
            while (isRun){
                String currentUlr =  webDriver.getCurrentUrl();
                if (tmpUrl!=currentUlr){
                    System.out.println("当前URL");
                    System.out.println(currentUlr);
                    tmpUrl = currentUlr;
                    if ("https://piaoju.jd.com/discount/own".equals(currentUlr)){
                        for (Cookie cookie:webDriver.manage().getCookies()){
                            bufferedWriter.write(cookie.getName()+"="+
                                    cookie.getValue()+";"+cookie.getDomain()+";"+cookie.getPath()+";"+cookie.getExpiry()+";"+cookie.isSecure());
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        fileWriter.close();
                        isRun = false;
                    }
                }
                Thread.sleep(1000);
            }
//            webDriver.quit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
