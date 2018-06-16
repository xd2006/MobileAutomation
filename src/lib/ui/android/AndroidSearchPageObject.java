package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_INPUT = "id:org.wikipedia:id/search_src_text";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SYBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
        SEARCH_RESULT_ELEMENT_TITLE = "id:org.wikipedia:id/page_list_item_title";
        SEARCH_RESULT_ELEMENT_DESCRIPTION = "id:org.wikipedia:id/page_list_item_description";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container' " +
                "and .//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}'] " +
                "and .//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";
    }

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }


}
