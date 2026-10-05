package org.example.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Tag("selenide")
class SelenideHomeworkTest extends BaseSelenideTest {
    @Test
    @DisplayName("2.1 Товар, созданный в админке, отображается на витрине")
    void adminCreatedProductAppearsInStorefront() {
        String name = data.newName();
        $("a[href='/admin']").click();
        $("[name='username']").setValue(UiSettings.USERNAME);
        $("[name='password']").setValue(UiSettings.password());
        $("button[type='submit']").click();
        $("#n-name").setValue(name);
        $("#n-price").setValue("75");
        $("#add-btn").click();
        $(".toast").shouldBe(visible, exactText("Товар успешно добавлен!"));
        $("a[href='/']").click();
        $(".product-card[data-name='" + name + "']").shouldBe(visible, attribute("data-price", "75"));
        $(".product-card[data-name='" + name + "'] h4").shouldBe(exactText(name));
    }

    @Test
    @DisplayName("2.2 Добавленный товар отображается в корзине")
    void addedProductAppearsInCart() {
        String name = data.newName();
        long id = data.create(name, 80);
        refresh();
        $("#card-" + id + " [data-action='add-to-cart']").click();
        $("#open-cart-btn").click();
        $("#cart-item-" + id + " b").shouldBe(visible, exactText(name));
        $("#cart-count").shouldBe(exactText("1"));
        $("#total-price").shouldBe(exactText("80"));
    }

    @Test
    @DisplayName("2.3 Неверные логин и пароль не дают войти в админку")
    void invalidCredentialsAreRejected() {
        $("a[href='/admin']").click();
        $("[name='username']").setValue("invalid-user");
        $("[name='password']").setValue("invalid-password");
        $("button[type='submit']").click();
        $(".alert-danger").shouldBe(visible, matchText("Bad credentials|Неверные учетные данные пользователя"));
        $("[name='username']").shouldBe(visible);
        $("#add-btn").shouldBe(not(exist));
    }

    @Test
    @DisplayName("2.4 Товары и количество сохраняются после обновления страницы")
    void cartSurvivesPageRefresh() {
        String name = data.newName();
        long id = data.create(name, 70);
        refresh();
        $("#q-" + id).setValue("2");
        $("#card-" + id + " [data-action='add-to-cart']").click();
        $("#open-cart-btn").click();
        $("#cart-item-" + id + " b").shouldBe(visible, exactText(name));
        $("#cart-count").shouldBe(exactText("2"));
        $("#total-price").shouldBe(exactText("140"));
        refresh();
        $("#open-cart-btn").click();
        $("#cart-count").shouldBe(exactText("2"));
        $("#cart-item-" + id + " b").shouldBe(visible, exactText(name));
        $("#cart-item-" + id + " .qty-controls span").shouldBe(exactText("2"));
        $("#total-price").shouldBe(exactText("140"));
    }

    @Test
    @DisplayName("2.5 Заказ дороже 300 рублей вызывает настоящий JS Alert")
    void orderAboveLimitShowsJavaScriptAlert() {
        String name = data.newName();
        long id = data.create(name, 101);
        refresh();
        $("#q-" + id).setValue("3");
        $("#card-" + id + " [data-action='add-to-cart']").click();
        $("#open-cart-btn").click();
        $("#total-price").shouldBe(exactText("303"));
        $("#makeOrder").click();
        // confirm() ждёт настоящий JS Alert и падает, если он не появился.
        // Alert не является DOM-элементом: читаем его только средствами Selenide,
        // затем проверяем захваченный текст через shouldBe и Condition.match.
        String alertText = confirm();
        String expected = "[SmartShop]: Денег не хватает! Сумма 303 ₽ превышает лимит 300 ₽.";
        $("#cartModal").shouldBe(visible,
                match("текст JS Alert: " + expected + "; получено: " + alertText,
                        ignored -> expected.equals(alertText)));
        $("#cart-item-" + id + " b").shouldBe(visible, exactText(name));
        $("#total-price").shouldBe(exactText("303"));
        $("#main-title").shouldBe(text("SmartShop"));
    }
}
