package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public abstract class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
//    private static String AppiumUrl = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        DesiredCapabilities capabilities = getCapabilitiesByPlatformEnv();
        URL appiumUrl = getAppiumUrlByPlatformEnv();

        driver = getDriverByPlatformEnv(appiumUrl, capabilities);
    }


    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgrounApp(int seconds) {
        driver.runAppInBackground(seconds);
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {

        String platform = System.getenv("PLATFORM");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {

            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("platformVersion", "6.0");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("app.Package", "org.wikipedia");
            capabilities.setCapability("app.Activity", ".main.MainActivity");
//        capabilities.setCapability("app", "C:\\Sources\\MobileAutomation\\apks\\org.wikipedia.apk");
            capabilities.setCapability("app", "C:\\Sources\\Study\\MobileAutomation\\apks\\org.wikipedia.apk");
        } else if (platform.equals(PLATFORM_IOS)) {


            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone SE");
            capabilities.setCapability("platformVersion", "11.2");
            capabilities.setCapability("app", "/Users/alex/Desktop/Wikipedia.app");
        }
            else {
            throw new Exception("Cannot get run platform value from env variable. Platform value " + platform);
        }
        return capabilities;
    }

    private AppiumDriver getDriverByPlatformEnv(URL appiumUrl, DesiredCapabilities capabilities) throws Exception {

        String platform = System.getenv("PLATFORM");
        AppiumDriver driver;

        if (platform.equals(PLATFORM_ANDROID)) {

            driver = new AndroidDriver(appiumUrl, capabilities);
        } else if (platform.equals(PLATFORM_IOS))
        {
            driver = new IOSDriver(appiumUrl, capabilities);
        }
        else{

            throw new Exception("Cannot get run platform value from env variable. Platform value " + platform);

        }

        return driver;
    }

    private URL getAppiumUrlByPlatformEnv() throws Exception {

        String platform = System.getenv("PLATFORM");
        String appiumUrl;

        if (platform.equals(PLATFORM_ANDROID)) {
            appiumUrl = "http://EPBYMINW6539.minsk.epam.com:4723/wd/hub";

        } else if (platform.equals(PLATFORM_IOS))
        {
            appiumUrl = "http://10.6.113.26:4723/wd/hub";;
        }
        else{

            throw new Exception("Cannot get run platform value from env variable. Platform value " + platform);

        }

        return new URL(appiumUrl);
    }
}
