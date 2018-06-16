package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {

     static {
                 TITLE = "id:org.wikipedia:id/view_page_title_text";
                 FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
                 OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
                 ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
                 ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
                 MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
                 MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
                 MY_LIST_FOLDER_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
                 CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
                 HEADER_ELEMENT = "id:org.wikipedia:id/page_header_view";
     }


    public AndroidArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
