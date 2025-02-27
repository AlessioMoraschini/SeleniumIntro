package google.chrome;

import google.common.CommonGoogleTest;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

/**
 * This works in case you have an available browser installed on the running machine.
 * If you want to run on e.g. a server not having Chrome, then you should use SeleniumGrid or use Docker to have a container with a Chrome instance
 *
 * "docker run -d --name selenium-chrome -p 4444:4444 selenium/standalone-chrome:latest"
 *
 *  And then
 *     protected RemoteWebDriver getWebDriver() {
 *         try {
 *             return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
 *         } catch (MalformedURLException e) {
 *             throw new RuntimeException(e);
 *         }
 *     }
 *
 * Or with Selenium Grid
 * "docker run -d -p 4444:4444 --name selenium-hub selenium/hub:latest"
 * "docker run -d --link selenium-hub:hub selenium/node-chrome:latest"
 * And then you can use the same java code as above.
 */
@DisplayName("Google Chrome Test")
public class GoogleTest extends CommonGoogleTest {
    @Override
    protected RemoteWebDriver getWebDriver() {
        // Let's go headless mode, in case UI not available
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        // Remove not needed messages
        options.addArguments("--silent");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setImplicitWaitTimeout(Duration.ofMillis(30));
        return new ChromeDriver(options);
    }
}
