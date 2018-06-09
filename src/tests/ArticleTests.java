package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {


    @Test
    public void testCompareArticleTitle() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        String title = articlePageObject.getArticleTitle();

        assertEquals("We see unexpected title",
                "Java (programming language)",
                title);

    }

    @Test
    public void testSwipeArticle() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();

    }

    @Test
    public void testCheckArticleTitle(){


        String searchText = "java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchText);
        searchPageObject.clickOnFirstResult();

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        articlePageObject.waitForArticleLoaded();
        articlePageObject.assertTitlePresent();

    }





}
