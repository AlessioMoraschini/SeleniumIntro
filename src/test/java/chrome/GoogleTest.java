package chrome;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleTest {

    private final ChromeDriver chromeDriver = new ChromeDriver();

    private Pair<WebElement, WebElement> commonCookiePageLoadAndAssert() {
        chromeDriver.get("https://www.google.it");

        // Let's get the "Accetta tutto" button for the initial cookie page
        WebElement acceptCookieButton = chromeDriver.findElement(By.id("L2AGLb"));

        assertEquals("Accetta tutto", acceptCookieButton.getText());

        // Let's get the "Rifiuta tutto" button for the initial cookie page
        WebElement rejectCookieButton = chromeDriver.findElement(By.id("W0wltc"));

        assertEquals("Rifiuta tutto", rejectCookieButton.getText());

        return Pair.of(acceptCookieButton, rejectCookieButton);
    }

    @ParameterizedTest
    @CsvSource({"true","false"})
    void reachabilityTestAccept(Boolean acceptCookies) {

        var acceptAndRejectBtns = commonCookiePageLoadAndAssert();

        // Let's get the overlay present to avoid clicking on buttons until we accept cookies
        // ( if not found then findElement() would throw NoSuchElementException)
        WebElement cookiesOverlay = chromeDriver.findElement(By.className("HTjtHe"));
        assertTrue(cookiesOverlay.isDisplayed());

        // Now let's accept or reject according to test case
        if(acceptCookies){
            acceptAndRejectBtns.getLeft().click();
        } else {
            acceptAndRejectBtns.getRight().click();
        }

        // And now after clicking let's ensure it is no more visible
        assertFalse(cookiesOverlay.isDisplayed());

        // TODO continue with more testing!
    }

    @AfterEach
    void closeTest() {
        chromeDriver.close();
    }
}
