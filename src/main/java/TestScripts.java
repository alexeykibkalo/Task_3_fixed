import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URISyntaxException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by China on 20.04.2017.
 */
public class TestScripts {

    private static String path = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
    private  static  String login = "webinar.test@gmail.com";
    private static String password = "Xcg7299bnSmMuRLp9ITw";
    private static String categoryName = "Kibkalo_test";

    public static EventFiringWebDriver getDriver()
    {
        System.setProperty("webdriver.chrome.driver"
                ,"src\\main\\resources\\chromedriver.exe");
        EventFiringWebDriver driver = new EventFiringWebDriver(new ChromeDriver());
        //driver.register(new EventHandler());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

    public static void signIn(WebDriver driver){

        driver.get(path);
        WebElement loginTB = driver.findElement(By.id("email"));
        loginTB.sendKeys(login);
        WebElement passTB = driver.findElement(By.id("passwd"));
        passTB.sendKeys(password);
        WebElement submit = driver.findElement(By.name("submitLogin"));
        submit.click();
    }

    public static  void addCategory(WebDriver driver){
        Actions builder = new Actions(driver);
        builder.moveToElement(driver.findElement(By.xpath("//li[@id='subtab-AdminCatalog']"))) .build().perform();
        driver. findElement(By.xpath("//li[@id='subtab-AdminCategories']")).click();
        WebElement add = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class = 'process-icon-new']")));
        add.click();

        driver.findElement(By.xpath("//input[@id='name_1']")).sendKeys(categoryName);
        driver.findElement(By.xpath("//input[@id='link_rewrite_1']")).sendKeys(categoryName);
        driver.findElement(By.xpath("//button[@id='category_form_submit_btn']")).click();

        add = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='alert alert-success']")));
        //категория добавлена
    }

    public static List<WebElement> checkCategory(WebDriver driver){

        driver.findElement(By.xpath("//span[contains(text(),'Имя')]/a/i[@class='icon-caret-up']")).click();

        List<WebElement>categories = driver.findElements(By.xpath("//tbody/tr"));
        for (WebElement element:categories
             ) {
            if(element.getText().contains(categoryName))
            {
                System.out.println("Catedory added");
                return categories;
            }
        }//button[contains(text(),'Удалить')]
        return categories;
    }

    public static void clean(WebDriver driver)
    {
        List<WebElement>categories = driver.findElements(By.xpath("//tbody/tr"));
        for (int i = 0; i<categories.size();i++) {
            //проверка нужна для того чтобы удалить только добавленную скриптом категорию
            //проход в цикле подчистит созданные ранее категории, если предидущий тест упал до метода clean()
            if(categoryName.contains(driver.findElement(By.xpath("//td[contains(text(),categoryName)]")).getText())) {
                driver.findElement(By.xpath("//td/div/div/button/i[@class='icon-caret-down']")).click();
                driver.findElement(By.xpath("//a[@class='delete']")).click();
                WebElement wait = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.presenceOfElementLocated
                                (By.xpath("//div[@class='panel']")));
                wait.findElement(By.xpath("//div/button/i[@class='icon-trash text-danger']")).click();
                wait = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.presenceOfElementLocated
                                (By.xpath("//div[@class='alert alert-success']")));
            }
        }
    }








}
