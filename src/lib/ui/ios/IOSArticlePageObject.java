package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        MY_LIST_FOLDER_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }



    public IOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
