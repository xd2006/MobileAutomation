package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;


public class IOSSearchPageObject extends SearchPageObject {


    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SYBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";

        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container' " +
                "and .//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}'] " +
                "and .//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";
    }




    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
