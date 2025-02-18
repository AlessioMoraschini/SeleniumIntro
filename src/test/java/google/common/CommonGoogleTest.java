package google.common;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CommonGoogleTest {

    // The specific test for a browser defines the specific webDriver to use but use cases remain the same
    protected abstract RemoteWebDriver getWebDriver();
    RemoteWebDriver webDriver = getWebDriver();


    private Pair<WebElement, WebElement> commonCookiePageLoadAndAssert() {
        webDriver.get("https://www.google.it");

        // Let's get the "Accetta tutto" button for the initial cookie page
        WebElement acceptCookieButton = webDriver.findElement(By.id("L2AGLb"));

        assertEquals("Accetta tutto", acceptCookieButton.getText());

        // Let's get the "Rifiuta tutto" button for the initial cookie page
        WebElement rejectCookieButton = webDriver.findElement(By.id("W0wltc"));

        assertEquals("Rifiuta tutto", rejectCookieButton.getText());

        return Pair.of(acceptCookieButton, rejectCookieButton);
    }

    private WebElement acceptOrRejectCookiesAndGetCookiesOverlay(boolean acceptCookies){
        var acceptAndRejectBtns = commonCookiePageLoadAndAssert();

        // Let's get the overlay present to avoid clicking on buttons until we accept cookies
        // ( if not found then findElement() would throw NoSuchElementException)
        WebElement cookiesOverlay = webDriver.findElement(By.className("HTjtHe"));
        assertTrue(cookiesOverlay.isDisplayed());

        // Now let's accept or reject according to test case
        if(acceptCookies){
            acceptAndRejectBtns.getLeft().click();
        } else {
            acceptAndRejectBtns.getRight().click();
        }

        return cookiesOverlay;
    }

    /**
     * Verify that the cookies overlay disappears after clicking the cookies acceptance buttons
     */
    @ParameterizedTest(name = "Test reachability with cookies acceptance: {0}")
    @CsvSource({"true","false"})
    void reachabilityTestAccept(Boolean acceptCookies) {

        var cookiesOverlay = acceptOrRejectCookiesAndGetCookiesOverlay(acceptCookies);

        // And now after clicking let's ensure it is no more visible
        assertFalse(cookiesOverlay.isDisplayed());
    }

    /**
     * Verify that the google main page contains the "Immagini" section
     */
    @ParameterizedTest(name = "Test image loading with cookies acceptance: {0}")
    @CsvSource({"true","false"})
    void imageLoadingTest(Boolean acceptCookies) {
        var acceptAndRejectBtns = commonCookiePageLoadAndAssert();

        if (acceptCookies) {
            acceptAndRejectBtns.getLeft().click();
        } else {
            acceptAndRejectBtns.getRight().click();
        }

        WebElement imagesLink = webDriver.findElement(By.linkText("Immagini"));
        imagesLink.click();

        assertTrue(webDriver.getTitle().contains("Google Immagini"));
    }

    /**
     * Verify that the search button works - not working because of Google's Recaptcha :)
     */
//    @ParameterizedTest
//    @CsvSource({"true","false"})
//    void searchFunctionalityTest(Boolean acceptCookies) {
//
//        acceptOrRejectCookiesAndGetCookiesOverlay(acceptCookies);
//
//        WebElement searchBox = webDriver.findElement(By.name("q"));
//        searchBox.sendKeys("Selenium WebDriver");
//        searchBox.submit();
//
//        assertTrue(webDriver.getTitle().contains("Selenium WebDriver"));
//    }

    @AfterEach
    void closeTest() {
        webDriver.close();
    }
}
