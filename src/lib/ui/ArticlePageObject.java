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
    CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";




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

    public void addArticleToMyList(String name_of_folder){

        waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options", 5);

        waitForElementAndClick(By.xpath(ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list", 5);

        waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay", 5);

        waitForElementAndClear(By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder", 5);

        waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT), name_of_folder,
                "Cannot put text into articles folder input", 5);

        waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press 'OK' button", 5);

    }

    public void closeArticle(){
        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X line", 5);
    }
}
