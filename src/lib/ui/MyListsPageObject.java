package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public static final String
    FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
    ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    private static String getFolderXpathByName(String name_of_folder){

        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String title){

        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", title);
    }

    public void openFolderByName(String name_of_folder){

        waitForElementAndClick(By.xpath(getFolderXpathByName(name_of_folder)),
                "Cannot find folder by name " + name_of_folder, 15);
    }

    public void swipeByArticleToDelete(String article_title){

        waitArticleToAppearByTitle(article_title);
        swipeElementToLeft(By.xpath(getSavedArticleXpathByTitle(article_title)),
                "Cannot find saved article");
        waitArticleToDisappearByTitle(article_title);
    }

    public void waitArticleToDisappearByTitle(String article_title){

        waitForElementNotPresent(By.xpath(getSavedArticleXpathByTitle(article_title)),
        "Save article still present with title " + article_title, 15);
    }

    public void waitArticleToAppearByTitle(String article_title){

        waitForElementPresent(By.xpath(getSavedArticleXpathByTitle(article_title)),
                "Cannot find saved article with title " + article_title, 15);
    }

    public void clickOnArticle(String article_name) {

        waitForElementAndClick(By.xpath(getSavedArticleXpathByTitle(article_name)),
                "Cannot open saved article " + article_name, 10);
    }
}
