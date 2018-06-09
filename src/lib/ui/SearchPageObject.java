package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SYBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* Template methods */
    private static String getResultSearchelement(String substring){

        return SEARCH_RESULT_BY_SYBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* Template methods */

    public void initSearchInput(){
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click iniit search element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5 );
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 10 );
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }


    public void typeSearchLine(String search_line){

        waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchresult(String substring){
        this.waitForElementPresent(By.xpath(getResultSearchelement(substring)), "Cannot find search result with substring " + substring,
                15);
    }

    public void clickByArticleWithSubstring(String substring){
        this.waitForElementAndClick(By.xpath(getResultSearchelement(substring)), "Cannot find and click search result with substring " + substring,
                15);
    }

    public int getAmountOfFoundArticles(){
        String search_result_locator = SEARCH_RESULT_ELEMENT;
        waitForElementPresent(By.xpath(search_result_locator), "Cannot find anything by request", 15);
        return getAmountOfElements(By.xpath(SEARCH_INIT_ELEMENT));
    }

    public void waitForEmptyResultsLabel(){

        String empty_result_label = SEARCH_EMPTY_RESULT_ELEMENT;

        waitForElementPresent(By.xpath(empty_result_label), "Cannot find empty results label", 15);
    }

    public void assertThereIsNoResultOfSearch(){
        assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We've found some results but supposed not to");
    }


}
