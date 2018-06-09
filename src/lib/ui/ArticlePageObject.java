package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    private static final String
    TITLE = "org.wikipedia:id/view_page_title_text",
    FOOTER_ELEMENT = "//*[@text='View page in browser']",
    OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
    ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
    ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
    MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
    MY_LIST_OK_BUTTON = "//*[@text='OK']",
    MY_LIST_FOLDER_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
    CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
    HEADER_ELEMENT = "org.wikipedia:id/page_header_view";



private String getMyListFolderNameXpath(String folder_name){
    return MY_LIST_FOLDER_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }


    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE),
                "Cannot find article title on page", 15);
    }

    public String getArticleTitle(){
        return waitForTitleElement().getText();
    }

    public void swipeToFooter(){

        this.swipeUpToElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of the article", 20 );
    }

    public void addArticleToMyListNewFolder(String name_of_folder){

        goToOptionsAddToMyList();

        waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay", 5);

        waitForElementAndClear(By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder", 5);

        waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT), name_of_folder,
                "Cannot put text into articles folder input", 5);

        waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press 'OK' button", 5);

    }

    public void addArticleToMyListToExistingFolder(String name_of_folder) {

        goToOptionsAddToMyList();

        waitForElementAndClick(By.xpath(getMyListFolderNameXpath(name_of_folder)),
                String.format("Can't find reading list %s", name_of_folder), 10);

    }


    public void closeArticle(){
        waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X line", 5);
    }

    public void waitForArticleLoaded() {
        waitForElementPresent(By.id(HEADER_ELEMENT), "Article wasn't loaded", 15);
    }

    public void assertTitlePresent() {
        assertElementPresent(By.id(TITLE), "Title of the article wasn't found");

    }

    private void goToOptionsAddToMyList(){
        waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options", 15);

        waitForElementAndClick(By.xpath(ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list", 15);
    }


}
