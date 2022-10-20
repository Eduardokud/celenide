import com.fasterxml.jackson.databind.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SiteTest {
    WebDriver driver;

    @BeforeAll
    public static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker()
            .enableVnc().enableRecording();

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void ValidData() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Иванов Олег");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79807665674");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void ValidNameWithHyphen() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Иванов-Олег");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+70000000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

}