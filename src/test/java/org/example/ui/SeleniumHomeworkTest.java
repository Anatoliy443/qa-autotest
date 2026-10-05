package org.example.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("ui")
@Tag("selenium")
class SeleniumHomeworkTest {
    private WebDriver browser;
    private WebDriverWait wait;
    private final UiTestData data = new UiTestData();

    @BeforeEach
    void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        if (UiSettings.HEADLESS) options.addArguments("--headless=new");
        options.addArguments("--window-size=1440,1000");
        browser = new ChromeDriver(options);
        wait = new WebDriverWait(browser, Duration.ofSeconds(8));
        browser.get(UiSettings.BASE_URL + "/");
        visible(By.id("main-title"));
    }

    @AfterEach
    void closeBrowserAndDeleteData() {
        try {
            if (browser != null) browser.quit();
        } finally {
            data.deleteCreatedGoods();
        }
    }

    @Test
    @DisplayName("1.1 Товар, созданный в админке, отображается на витрине")
    void adminCreatedProductAppearsInStorefront() {
        String name = data.newName();
        visible(By.cssSelector("a[href='/admin']")).click();
        visible(By.name("username")).sendKeys(UiSettings.USERNAME);
        visible(By.name("password")).sendKeys(UiSettings.password());
        visible(By.cssSelector("button[type='submit']")).click();
        visible(By.id("n-name")).sendKeys(name);
        visible(By.id("n-price")).sendKeys("75");
        visible(By.id("add-btn")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".toast"), "Товар успешно добавлен!"));
        visible(By.cssSelector("a[href='/']")).click();
        WebElement card = visible(By.cssSelector(".product-card[data-name='" + name + "']"));
        assertEquals(name, card.findElement(By.tagName("h4")).getText());
        assertEquals("75", card.getAttribute("data-price"));
    }

    @Test
    @DisplayName("1.2 Добавленный товар отображается в корзине")
    void addedProductAppearsInCart() {
        String name = data.newName();
        long id = data.create(name, 80);
        browser.navigate().refresh();
        visible(By.cssSelector("#card-" + id + " [data-action='add-to-cart']")).click();
        visible(By.id("open-cart-btn")).click();
        assertEquals(name, visible(By.cssSelector("#cart-item-" + id + " b")).getText());
        assertEquals("1", visible(By.id("cart-count")).getText());
        assertEquals("80", visible(By.id("total-price")).getText());
    }

    @Test
    @DisplayName("1.3 Неверные логин и пароль не дают войти в админку")
    void invalidCredentialsAreRejected() {
        visible(By.cssSelector("a[href='/admin']")).click();
        visible(By.name("username")).sendKeys("invalid-user");
        visible(By.name("password")).sendKeys("invalid-password");
        visible(By.cssSelector("button[type='submit']")).click();
        assertTrue(visible(By.cssSelector(".alert-danger")).getText().matches("Bad credentials|Неверные учетные данные пользователя"));
        assertTrue(visible(By.name("username")).isDisplayed());
        assertFalse(browser.findElements(By.id("add-btn")).size() > 0);
    }

    @Test
    @DisplayName("1.4 Товары и количество сохраняются после обновления страницы")
    void cartSurvivesPageRefresh() {
        String name = data.newName();
        long id = data.create(name, 70);
        browser.navigate().refresh();
        visible(By.id("q-" + id)).clear();
        visible(By.id("q-" + id)).sendKeys("2");
        visible(By.cssSelector("#card-" + id + " [data-action='add-to-cart']")).click();
        visible(By.id("open-cart-btn")).click();
        assertEquals(name, visible(By.cssSelector("#cart-item-" + id + " b")).getText());
        assertEquals("2", visible(By.id("cart-count")).getText());
        assertEquals("140", visible(By.id("total-price")).getText());
        browser.navigate().refresh();
        visible(By.id("open-cart-btn")).click();
        assertEquals("2", visible(By.id("cart-count")).getText(), "Корзина должна сохраняться после обновления");
        assertEquals(name, visible(By.cssSelector("#cart-item-" + id + " b")).getText());
        assertEquals("2", visible(By.cssSelector("#cart-item-" + id + " .qty-controls span")).getText());
        assertEquals("140", visible(By.id("total-price")).getText());
    }

    private WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
