import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;

/**
 * Created by China on 20.04.2017.
 */
public class MainTest extends TestScripts {
    private List<WebElement>categories;
    public static void main(String[] args) {
        EventFiringWebDriver driver = getDriver();
        signIn(driver);
        addCategory(driver);
        checkCategory(driver);
        clean(driver);

    }

}
