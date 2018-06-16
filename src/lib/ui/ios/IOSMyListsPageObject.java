package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {

    static
    {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        SYNC_REQUEST_POPUP_CLOSE_BUTTON = "id:places auth close";
    }

    public IOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
