package lib.ui;

import io.appium.java_client.AppiumDriver;
import javafx.util.Pair;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "id:org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SYBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
            SEARCH_RESULT_ELEMENT_TITLE = "id:org.wikipedia:id/page_list_item_title",
            SEARCH_RESULT_ELEMENT_DESCRIPTION = "id:org.wikipedia:id/page_list_item_description",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container' " +
                    "and .//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}'] " +
                    "and .//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";




    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* Template methods */
    private static String getResultSearchElementXpath(String substring){

        return SEARCH_RESULT_BY_SYBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description){
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }

    /* Template methods */

    public void initSearchInput(){
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click iniit search element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5 );
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 10 );
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
        waitForEmptyResultsList();
    }


    public void typeSearchLine(String search_line){

        waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring){
        this.waitForElementPresent(getResultSearchElementXpath(substring), "Cannot find search result with substring " + substring,
                15);
    }

    public void clickByArticleWithSubstring(String substring){
        this.waitForElementAndClick(getResultSearchElementXpath(substring), "Cannot find and click search result with substring " + substring,
                15);
    }

    public int getAmountOfFoundArticles(){
        String search_result_locator = SEARCH_RESULT_ELEMENT;
        waitForElementPresent(search_result_locator, "Cannot find anything by request", 15);
        return getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel(){

        String empty_result_label = SEARCH_EMPTY_RESULT_ELEMENT;

        waitForElementPresent(empty_result_label, "Cannot find empty results label", 15);
    }

    public void assertThereIsNoResultOfSearch(){
        assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We've found some results but supposed not to");
    }


    public String getSearchInputText() {
        WebElement search_input_element = waitForElementPresent(SEARCH_INPUT,
                "Cannot find search input element",
                15);

        return search_input_element.getText();
    }

    public void waitForEmptyResultsList() {
       waitForElementNotPresent(SEARCH_RESULT_ELEMENT,
                "There should be no search results but some were found", 15);
    }

    public Pair<Boolean, String> assertThatAllResultsAreValidForSearchRequest(String searchText) {
        List<WebElement> articles = waitForElementsPresent(SEARCH_RESULT_ELEMENT,
                "Results weren't found", 15);

        String errorMessage="";
        Integer i = 1;
        for (WebElement article : articles) {
            String title = article.findElement(getLocatorByString(SEARCH_RESULT_ELEMENT_TITLE)).getText();
            WebElement descriptionElement = waitForNestedElementPresent(article,
                    SEARCH_RESULT_ELEMENT_DESCRIPTION, 1);
            String description = descriptionElement == null ? "" : descriptionElement.getText();

            if (!(title.toLowerCase().contains(searchText.toLowerCase()) ||
                    description.toLowerCase().contains(searchText.toLowerCase()))) {
                errorMessage += String.format("%d. '%s' \n", i, title );
            }
            i++;
        }
        Boolean valid = errorMessage.equals("");
        Pair<Boolean, String> result = new Pair<>(valid, errorMessage);

        return result;
    }

    public void clickOnFirstResult() {
        waitForElementAndClick(SEARCH_RESULT_ELEMENT,
                "Result article wasn't found", 15);
    }

    public List<String> getResultsTitles(int numberOfresults) {

        List<WebElement> articles = waitForElementsPresent(SEARCH_RESULT_ELEMENT,
                "Results weren't found", 15);

        List<String> titles = new ArrayList<>();

        for (int i = 0; i < numberOfresults; i++) {
            titles.add(articles.get(i).findElement(getLocatorByString(SEARCH_RESULT_ELEMENT_TITLE)).getText());
        }
        return titles;
    }

    public void waitForElementByTitleAndDescription(String title, String description){
        String xpath = getResultSearchElementByTitleAndDescription(title, description);
        waitForElementPresent(xpath,
                String.format("Search result with title '%s' and description '%s' wasn't found", title, description),
                10);
    }
}
