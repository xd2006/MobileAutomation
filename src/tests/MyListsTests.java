package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUi;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUiPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList(){

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyListNewFolder(name_of_folder);
        } else{
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUi navigationUi = NavigationUiPageObjectFactory.get(driver);
        navigationUi.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        else {
            myListsPageObject.CloseSyncRequestPopup();
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testArticlesFoldering() {

        String search_line = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        List<String> titles = searchPageObject.getResultsTitles(2);

         searchPageObject.clickByArticleWithSubstring(titles.get(0));


        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForArticleLoaded();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyListNewFolder(name_of_folder);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();

        if (Platform.getInstance().isAndroid()) {
            searchPageObject.typeSearchLine(search_line);
        }
        searchPageObject.clickByArticleWithSubstring(titles.get(1));

        articlePageObject.waitForArticleLoaded();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyListToExistingFolder(name_of_folder);
        } else{
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();


        NavigationUi navigationUi = NavigationUiPageObjectFactory.get(driver);
        navigationUi.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        else {
            myListsPageObject.CloseSyncRequestPopup();
        }

        myListsPageObject.swipeByArticleToDelete(titles.get(0));

        myListsPageObject.clickOnArticle(titles.get(1));


        articlePageObject.waitForArticleLoaded();

        if (Platform.getInstance().isAndroid()) {
            String title_of_remained_article = articlePageObject.getArticleTitle();

            assertEquals(String.format("Wrong title of the article found. Should be '%s' but was found '%s'", titles.get(1), title_of_remained_article)
                    , titles.get(1), title_of_remained_article);
        }
        else{
            String title = titles.get(1).split("\n",2)[0];
            articlePageObject.CheckTextDisplayedOnPage(title);
        }

    }
}
