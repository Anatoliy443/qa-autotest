package org.example.ui;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@Tag("ui")
abstract class BaseSelenideTest {
    protected final UiTestData data = new UiTestData();

    @BeforeEach
    void startBrowser() {
        Configuration.baseUrl = UiSettings.BASE_URL;
        Configuration.browser = "chrome";
        Configuration.headless = UiSettings.HEADLESS;
        Configuration.browserSize = "1440x1000";
        Configuration.timeout = 8000;
        Configuration.reportsFolder = "build/reports/tests/uiTest/screenshots";
        open("/");
        $("#main-title").shouldBe(visible);
    }

    @AfterEach
    void closeBrowserAndDeleteData() {
        try {
            closeWebDriver();
        } finally {
            data.deleteCreatedGoods();
        }
    }
}
