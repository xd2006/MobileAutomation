package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationUi extends MainPageObject {
    public NavigationUi(AppiumDriver driver) {
        super(driver);
    }

    private static final String
                MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public void clickMyLists(){

        waitForElementAndClick(MY_LISTS_LINK,
                "Cannot find navigation button to 'My lists'", 15);
    }
}
