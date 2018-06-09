package tests;

import javafx.util.Pair;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

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
    public void testAmountOfNonEmptySearch(){


        String search_line = "Linkin Park Discography";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();
        assertTrue("We found too few results", amount_of_search_results > 0);
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
    public void testCheckSearchInput() {

        String expectedText = "Search…";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        String input_text = searchPageObject.getSearchInputText();

        assertEquals(String.format("%s wasn't found", expectedText), expectedText, input_text);

    }

    @Test
    public void testCancelSearchAfterFind() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");

        int articles = searchPageObject.getAmountOfFoundArticles();

        assertTrue("Less than 2 articles were found", articles > 1);

        searchPageObject.clickCancelSearch();

        searchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testCheckSearchResults() {

        String searchText = "java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchText);
        Pair<Boolean, String> result = searchPageObject.assertThatAllResultsAreValidForSearchRequest(searchText);

        assertTrue(String.format("Not all results contain search text '%s'. Invalid results are: \n%s",
                searchText, result.getValue()),
                result.getKey());
    }

    @Test
    public void testCheckSearchResultsByTitleAndDescription() {

        String searchText = "java";
        Map<String, String> expectedResults = new HashMap<>();
        expectedResults.put("Java", "Island of Indonesia");
        expectedResults.put("Java (programming language)", "Object-oriented programming language");
        expectedResults.put("JavaScript", "Programming language");

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchText);

        expectedResults.forEach((k, v) -> searchPageObject.waitForElementByTitleAndDescription(k, v));


    }
}
