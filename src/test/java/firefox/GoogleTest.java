package firefox;

import common.CommonGoogleTest;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * This works in case you have an available browser installed on the running machine.
 * If you want to run on e.g. a server not having Chrome, then you should use SeleniumGrid or use Docker to have a container with a Chrome instance
 *
 * "docker run -d --name selenium-firefox -p 4444:4444 selenium/standalone-firefox:latest"
 *
 *  And then
 *     protected RemoteWebDriver getWebDriver() {
 *         try {
 *             return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
 *         } catch (MalformedURLException e) {
 *             throw new RuntimeException(e);
 *         }
 *     }
 *
 * Or with Selenium Grid
 * "docker run -d -p 4444:4444 --name selenium-hub selenium/hub:latest"
 * "docker run -d --link selenium-hub:hub selenium/node-firefox:latest"
 * And then you can use the same java code as above.
 */
public class GoogleTest extends CommonGoogleTest {
    @Override
    protected RemoteWebDriver getWebDriver() {
        // Let's go headless mode, in case UI not available
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        return new FirefoxDriver(options);
    }
}
