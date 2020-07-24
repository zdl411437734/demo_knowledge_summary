package com.zdltech.demo.seleniumdemo;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarResponse;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
public class SeleniumDemoCookiesbak {
    public static void main(String[] args) {
        //开始代理
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(0);

        //获取 Selenium的代理
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        //浏览器安装路径
        //chromedriver位置
        String chromeriverPath = "/Users/jason/Documents/opt/java_workspace/demo_knowledge_summary/files/chromedriver";
        System.setProperty("webdriver.chrome.driver",chromeriverPath);
        //测试连接
        String url  = "https://www.baidu.com/";
        //启动webdriver
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.PROXY,seleniumProxy);
        WebDriver webDriver = new ChromeDriver(options);
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
                        proxy.newHar("https://piaoju.jd.com/discount/own");
                        for (Cookie cookie:webDriver.manage().getCookies()){
                            bufferedWriter.write(cookie.getName()+"="+
                                    cookie.getValue()+";"+cookie.getDomain()+";"+cookie.getPath()+";"+cookie.getExpiry()+";"+cookie.isSecure());
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        fileWriter.close();
                        isRun = false;
                        System.out.println("获取har");
                        Har har = proxy.getHar();
                        //TODO  通过webDriver访问想获取的URL地址
                        List<HarEntry> list =  har.getLog().getEntries();
                        for (HarEntry harEntry : list){
                            String urlTmp = harEntry.getRequest().getUrl();

                            if(!urlTmp.startsWith("https://piaoju.jd.com/discount")){
                                continue;
                            }
                            HarResponse harResponse = harEntry.getResponse();
                            String responseBody = harResponse.getContent().getText();

//                            LOGGER.info(" harEntry.getRequest().getUrl() {}",urlTmp);
                            System.out.println(urlTmp);
                            System.out.println(responseBody);
//                            LOGGER.info("responseBody {} ",responseBody);
                        }

                        //处理自己的业务逻辑
                        proxy.endHar();
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
