package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

public abstract class ArticlePageObject extends MainPageObject {
    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    protected static String
    TITLE,
    FOOTER_ELEMENT,
    OPTIONS_BUTTON,
    ADD_TO_MY_LIST_BUTTON,
    ADD_TO_MY_LIST_OVERLAY,
    MY_LIST_NAME_INPUT,
    MY_LIST_OK_BUTTON,
    MY_LIST_FOLDER_NAME_TPL,
    CLOSE_ARTICLE_BUTTON,
    HEADER_ELEMENT;



private String getMyListFolderNameXpath(String folder_name){
    return MY_LIST_FOLDER_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }


    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE,
                "Cannot find article title on page", 15);
    }

    public String getArticleTitle(){
    if (Platform.getInstance().isAndroid() )
    {    return waitForTitleElement().getText();}
    else{
         return waitForTitleElement().getAttribute("name");
    }
    }

    public void swipeToFooter(){

    if (Platform.getInstance().isAndroid()) {
        this.swipeUpToElement(FOOTER_ELEMENT, "Cannot find the end of the article", 50);
    }
    else {
        swipeUpTillElementAppear(FOOTER_ELEMENT,"Cannot find the end of the article", 50 );
    }
    }

    public void addArticleToMyListNewFolder(String name_of_folder){

        goToOptionsAddToMyList();

        waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay", 5);

        waitForElementAndClear(MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder", 5);

        waitForElementAndSendKeys(MY_LIST_NAME_INPUT, name_of_folder,
                "Cannot put text into articles folder input", 5);

        waitForElementAndClick(MY_LIST_OK_BUTTON,
                "Cannot press 'OK' button", 5);

    }

    public void addArticleToMyListToExistingFolder(String name_of_folder) {

        goToOptionsAddToMyList();

        waitForElementAndClick(getMyListFolderNameXpath(name_of_folder),
                String.format("Can't find reading list %s", name_of_folder), 10);

    }


    public void closeArticle(){
        waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X line", 5);
    }

    public void waitForArticleLoaded() {
        waitForElementPresent(HEADER_ELEMENT, "Article wasn't loaded", 15);
    }

    public void assertTitlePresent() {
        assertElementPresent(TITLE, "Title of the article wasn't found");

    }

    private void goToOptionsAddToMyList(){
        waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find button to open article options", 15);

        waitForElementAndClick(ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list", 15);
    }


}
