package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public abstract class iOSTestCase extends TestCase {

    protected AppiumDriver driver;
//    private static String AppiumUrl = "http://127.0.0.1:4723/wd/hub";
   private static String AppiumUrl = "http://10.6.113.11:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("platformVersion", "11.2");
        capabilities.setCapability("app", "/Users/alex/Desktop/Wikipedia.app");



        driver = new IOSDriver(new URL(AppiumUrl), capabilities);
    }


    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgrounApp(int seconds){
        driver.runAppInBackground(seconds);
    }



}
