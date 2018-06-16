package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

public abstract class MyListsPageObject extends MainPageObject {
    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    protected static String
            SYNC_REQUEST_POPUP_CLOSE_BUTTON,
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL;

    private static String getFolderXpathByName(String name_of_folder){

        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String title){

        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", title);
    }

    public void CloseSyncRequestPopup() {

        waitForElementAndClick(SYNC_REQUEST_POPUP_CLOSE_BUTTON,
                "Cannot find close button for sync request popup", 5);
    }

    public void openFolderByName(String name_of_folder){

        waitForElementAndClick(getFolderXpathByName(name_of_folder),
                "Cannot find folder by name " + name_of_folder, 15);
    }

    public void swipeByArticleToDelete(String article_title){

        waitArticleToAppearByTitle(article_title);
        String articleXpath = getSavedArticleXpathByTitle(article_title);
        swipeElementToLeft(articleXpath,
                "Cannot find saved article");

        if (Platform.getInstance().isIOS()){
            clickElementToTheRightUpperCorner(articleXpath, "Cannot find save article ");
        }

        waitArticleToDisappearByTitle(article_title);
    }

    public void waitArticleToDisappearByTitle(String article_title){

        waitForElementNotPresent(getSavedArticleXpathByTitle(article_title),
        "Save article still present with title " + article_title, 15);
    }

    public void waitArticleToAppearByTitle(String article_title){

        waitForElementPresent(getSavedArticleXpathByTitle(article_title),
                "Cannot find saved article with title " + article_title, 15);
    }

    public void clickOnArticle(String article_name) {

        waitForElementAndClick(getSavedArticleXpathByTitle(article_name),
                "Cannot open saved article " + article_name, 10);
    }
}
