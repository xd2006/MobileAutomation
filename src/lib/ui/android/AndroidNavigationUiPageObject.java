package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUi;

public class AndroidNavigationUiPageObject extends NavigationUi {

    static { MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";}

    public AndroidNavigationUiPageObject(AppiumDriver driver) {
        super(driver);
    }
}
