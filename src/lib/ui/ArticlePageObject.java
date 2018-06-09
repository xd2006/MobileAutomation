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
    FOOTER_ELEMENT = "//*[@text='View page in browser']";

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
}
