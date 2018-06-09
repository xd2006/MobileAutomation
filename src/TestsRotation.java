import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;

public class TestsRotation extends CoreTestCase {

    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception {

        super.setUp();
        MainPageObject = new MainPageObject(driver);

    }


    @Override
    public void tearDown() throws Exception {

        rotateScreenPortrait();
        super.tearDown();
    }


    @Test
    public void testChangeScreenOrientationOnResultsScreen(){

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String title_before_rotation = articlePageObject.getArticleTitle();

        rotateScreenLandscape();

        String title_after_rotation = articlePageObject.getArticleTitle();

        Assert.assertEquals("Article title has been changed after screen rotation", title_before_rotation, title_after_rotation);

        rotateScreenPortrait();

        String title_after_second_rotation = articlePageObject.getArticleTitle();

        Assert.assertEquals("Article title has been changed after screen rotation", title_before_rotation, title_after_second_rotation);

    }
}
