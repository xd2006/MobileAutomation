package lib.ui;

import io.appium.java_client.AppiumDriver;

public abstract class NavigationUi extends MainPageObject {
    public NavigationUi(AppiumDriver driver) {
        super(driver);
    }

    protected static String
                MY_LISTS_LINK;

    public void clickMyLists(){

        waitForElementAndClick(MY_LISTS_LINK,
                "Cannot find navigation button to 'My lists'", 15);
    }
}
