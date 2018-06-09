import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Tests extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {

        super.setUp();
        MainPageObject = new MainPageObject(driver);

    }

    @Test
    public void testSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchresult("Object-oriented programming language");

    }


    @Test
    public void testCheckSearchInput() {
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15);

        WebElement search_input_element = MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search input element",
                15);

        String input_text = search_input_element.getText();

        Assert.assertEquals("'Search…' wasn't found", "Search…", input_text);

    }


    @Test
    public void testCancelSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testCancelSearchAfterFind() {

        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                15);


        List<WebElement> articles = MainPageObject.waitForElementsPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Results weren't found", 15);

        Assert.assertTrue("Less than 2 articles were found", articles.size() > 1);

        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        MainPageObject.waitForElementNotPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Results are still present on the page",
                5
        );
    }

    @Test
    public void testCheckSearchResults() {

        String searchText = "java";

        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "Cannot find search input",
                15);


        List<WebElement> articles = MainPageObject.waitForElementsPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Results weren't found", 15);

       String errorMessage="";
       Integer i = 1;
        for (WebElement article : articles) {
            String title = article.findElement(By.id("org.wikipedia:id/page_list_item_title")).getText();
            WebElement descriptionElement = MainPageObject.waitForNestedElementPresent(article,
                    By.id("org.wikipedia:id/page_list_item_description"), 1);
            String description = descriptionElement == null ? "" : descriptionElement.getText();

            if (!(title.toLowerCase().contains(searchText.toLowerCase()) ||
                    description.toLowerCase().contains(searchText.toLowerCase()))) {
                errorMessage += String.format("%d. '%s' \n", i, title );
            }
            i++;
        }

        Assert.assertEquals(String.format("Not all results contain search text '%s'. Invalid results are: \n%s",
                searchText, errorMessage),
                "", errorMessage);

    }

    @Test
    public void testCompareArticleTitle() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        String title = articlePageObject.getArticleTitle();

        Assert.assertEquals("We see unexpected title",
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
    public void testSaveFirstArticleToMyList(){

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        String name_of_folder = "Learning programming";

        articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUi navigationUi = new NavigationUi(driver);
        navigationUi.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);

        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testAmountOfNonEmptySearch(){


        String search_line = "Linkin Park Discography";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue("We found too few results", amount_of_search_results > 0);
    }

    @Test
    public void testAmountOfEmptySearch(){

        String search_line = "asaljljejekl";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testCheckSearchArticleInBackground(){

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchresult("Object-oriented programming language");

        backgrounApp(2);

        searchPageObject.waitForSearchresult("Object-oriented programming language");

    }

    @Test
    public void testCheckArticleTitle(){

        String searchText = "java";

        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "Cannot find search input",
                15);


        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/page_list_item_container"),
                "Result article wasn't found", 15);

        MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/page_header_view"), "Article wasn't loaded", 15);

        MainPageObject.assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Title of the article wasn't found");
    }

    @Test
    public void testArticlesFoldering() {

        String search_line = "Java";

        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15);

        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                15);

        List<WebElement> articles = MainPageObject.waitForElementsPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Results weren't found", 15);

        List<String> titles = new ArrayList<>();

        String name_of_folder = "Learning programming";
        for (int i = 0; i < 2; i++) {
            titles.add(articles.get(i).findElement(By.id("org.wikipedia:id/page_list_item_title")).getText());
        }

        MainPageObject.waitForElementAndClick(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", titles.get(0))),
                "Cannot find result article",
                15);

        MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Article wasn't loaded",
                    30);

        MainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options", 15);

        MainPageObject.waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list", 15);

        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay", 5);

        MainPageObject.waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder", 5);

        MainPageObject.waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"), name_of_folder,
                "Cannot put text into articles folder input", 5);

        MainPageObject.waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button", 5);

        MainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X line", 5);



        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15);

        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                15);

        MainPageObject.waitForElementAndClick(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']",
                titles.get(1))),
                "Cannot find result article",
                15);


        MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Article wasn't loaded",
                30);

        MainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options", 15);

        MainPageObject.waitForElementAndClick(By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list", 15);

        MainPageObject.waitForElementAndClick(By.xpath(String.format("//*[@text='%s']", name_of_folder)),
                 String.format("Can't find reading list %s", name_of_folder), 10);

        MainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X line", 10);

        MainPageObject.waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to 'My lists'", 5);

        MainPageObject.waitForElementAndClick(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder", 30);

        MainPageObject.swipeElementToLeft(By.xpath(String.format("//*[@text='%s']", titles.get(0))),
                "Cannot find saved article with title" + titles.get(0) );

        MainPageObject.waitForElementNotPresent(By.xpath(String.format("//*[@text='%s']", titles.get(0))),
                "Cannot delete saved article", 15);

        MainPageObject.waitForElementAndClick(By.xpath(String.format("//*[@text='%s']", titles.get(1))),
                "Cannot open saved article " + titles.get(1), 10);

        String title_of_remained_article = MainPageObject.waitForWebElementAndGetAttribute(By.id("org.wikipedia:id/view_page_title_text"), "text",
                "Cannot find title of article", 15);

        Assert.assertEquals(String.format("Wrong title of the article found. Should be '%s' but was found '%s'", titles.get(1), title_of_remained_article)
                , titles.get(1), title_of_remained_article);

    }

}
