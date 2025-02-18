package google.edge;

import google.common.CommonGoogleTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * This works in case you have an available browser installed on the running machine.
 * If you want to run on e.g. a server not having Chrome, then you should use SeleniumGrid or use Docker to have a container with a Chrome instance
 *
 * "docker run -d --name selenium-edge -p 4444:4444 selenium/standalone-edge:latest"
 *
 *  And then
 *     protected RemoteWebDriver getWebDriver() {
 *         try {
 *             return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.edge());
 *         } catch (MalformedURLException e) {
 *             throw new RuntimeException(e);
 *         }
 *     }
 *
 * Or with Selenium Grid
 * "docker run -d -p 4444:4444 --name selenium-hub selenium/hub:latest"
 * "docker run -d --link selenium-hub:hub selenium/node-edge:latest"
 * And then you can use the same java code as above.
 */
@Disabled
@DisplayName("MS Edge Test")
public class GoogleTest extends CommonGoogleTest {
    @Override
    protected RemoteWebDriver getWebDriver() {
        // Let's go headless mode, in case UI not available
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");
        return new EdgeDriver(options);
    }
}
