package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUi;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList(){

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        String name_of_folder = "Learning programming";

        articlePageObject.addArticleToMyListNewFolder(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUi navigationUi = new NavigationUi(driver);
        navigationUi.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);

        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testArticlesFoldering() {

        String search_line = "Java";
        String name_of_folder = "Learning programming";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        List<String> titles = searchPageObject.getResultsTitles(2);

        searchPageObject.clickByArticleWithSubstring(titles.get(0));

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForArticleLoaded();

        articlePageObject.addArticleToMyListNewFolder(name_of_folder);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        searchPageObject.clickByArticleWithSubstring(titles.get(1));
        articlePageObject.waitForArticleLoaded();

        articlePageObject.addArticleToMyListToExistingFolder(name_of_folder);
        articlePageObject.closeArticle();


        NavigationUi navigationUi = new NavigationUi(driver);
        navigationUi.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);

        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(titles.get(0));


        myListsPageObject.waitArticleToDisappearByTitle(titles.get(0));

        myListsPageObject.clickOnArticle(titles.get(1));

        articlePageObject.waitForArticleLoaded();
        String title_of_remained_article = articlePageObject.getArticleTitle();

        assertEquals(String.format("Wrong title of the article found. Should be '%s' but was found '%s'", titles.get(1), title_of_remained_article)
                , titles.get(1), title_of_remained_article);

    }
}
