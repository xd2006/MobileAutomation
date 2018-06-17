package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    private static Platform instance;
    private Platform(){}

    public static Platform getInstance(){
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception {

        URL url = getAppiumUrlByPlatformEnv();
        if (isAndroid()){
            return new AndroidDriver(url, getAndroidDesiredCapabilities());
        } else if (isIOS()){
            return new IOSDriver(url, getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the driver. Platform value: " + getPlatformVar());
        }
    }

    public Boolean isAndroid(){
        return isPlatform(PLATFORM_ANDROID);
    }

    public Boolean isIOS(){
        return isPlatform(PLATFORM_IOS);
    }



    private DesiredCapabilities getAndroidDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app.Package", "org.wikipedia");
        capabilities.setCapability("app.Activity", ".main.MainActivity");
//        capabilities.setCapability("app", "C:\\Sources\\MobileAutomation\\apks\\org.wikipedia.apk");
        capabilities.setCapability("app", "C:\\Sources\\Study\\MobileAutomation\\apks\\org.wikipedia.apk");

        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities(){

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("platformVersion", "11.2");
        capabilities.setCapability("app", "/Users/alex/Desktop/Wikipedia.app");

        return capabilities;
    }

    private Boolean isPlatform(String my_platform){
        String platform = getPlatformVar();
        return my_platform.equals(platform);

    }

    private String getPlatformVar(){
        return System.getenv("PLATFORM");
    }

    private URL getAppiumUrlByPlatformEnv() throws Exception {

        String platform = getPlatformVar();
        String appiumUrl;

        if (platform.equals(PLATFORM_ANDROID)) {
            appiumUrl = "http://EPBYMINW6539.minsk.epam.com:4723/wd/hub";

        } else if (platform.equals(PLATFORM_IOS))
        {
            appiumUrl = "http://10.6.113.4:4723/wd/hub";
        }
        else{

            throw new Exception("Cannot get run platform value from env variable. Platform value " + platform);

        }

        return new URL(appiumUrl);
    }
}
