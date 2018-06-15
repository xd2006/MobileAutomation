package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;

public abstract class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    protected Platform platform;

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        platform = new Platform();
        driver = platform.getDriver();
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


}
