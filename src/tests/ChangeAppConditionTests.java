package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Override
    public void tearDown() throws Exception {

        rotateScreenPortrait();
        super.tearDown();
    }


    @Test
    public void testCheckSearchArticleInBackground(){

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        backgrounApp(2);

        searchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testChangeScreenOrientationOnResultsScreen(){

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String title_before_rotation = articlePageObject.getArticleTitle();

        rotateScreenLandscape();

        String title_after_rotation = articlePageObject.getArticleTitle();

        assertEquals("Article title has been changed after screen rotation", title_before_rotation, title_after_rotation);

        rotateScreenPortrait();

        String title_after_second_rotation = articlePageObject.getArticleTitle();

        assertEquals("Article title has been changed after screen rotation", title_before_rotation, title_after_second_rotation);

    }

}
