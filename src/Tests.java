import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class Tests {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app.Package", "org.wikipedia");
        capabilities.setCapability("app.Activity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Sources\\MobileAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchTest() {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                15);

        waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15);

    }

    @Test
    public void checkSearchInputTest() {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15);

        WebElement search_input_element = waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find article title",
                15);

        String input_text = search_input_element.getText();

        Assert.assertEquals("'Search…' wasn't found", "Search…", input_text);

    }


    @Test
    public void cancelSearchTest() {

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                15);

        waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search input",
                10);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void cancelSearchAfterFindTest() {

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                15);


        List<WebElement> articles = waitForElementsPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Results weren't found", 15);

        Assert.assertTrue("Less than 2 articles were found", articles.size() > 1);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Results are still present on the page",
                5
        );
    }

    @Test
    public void checkSearchResultsTest() {

        String searchText = "java";

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "Cannot find search input",
                15);


        List<WebElement> articles = waitForElementsPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Results weren't found", 15);

       String errorMessage="";
        for (WebElement article : articles) {
            String title = article.findElement(By.id("org.wikipedia:id/page_list_item_title")).getText();
            WebElement descriptionElement = waitForNestedElementPresent(article,
                    By.id("org.wikipedia:id/page_list_item_description"), 1);
            String description = descriptionElement == null ? "" : descriptionElement.getText();

            if (!(title.toLowerCase().contains(searchText.toLowerCase()) ||
                    description.toLowerCase().contains(searchText.toLowerCase()))) {
                errorMessage += String.format("Title: '%s' \n", title );
            }
        }

        Assert.assertEquals(String.format("Not all results contain search text '%s'. Invalid results are: \n%s",
                searchText, errorMessage),
                "", errorMessage);

    }

    @Test
    public void compareArticleTitle() {

        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                15);

        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                15);

        WebElement title_element = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        String title = title_element.getText();

        Assert.assertEquals("We see unexpected title",
                "Java (programming language)",
                title);

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private List<WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((by)));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 10);
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }


    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }


    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement waitForNestedElementPresent(WebElement element, By by, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        try {
            return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, by));
        } catch (WebDriverException e) {
            return null;
        }
    }
}
