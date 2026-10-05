package org.example.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Tag("webinar")
class WebinarHomeworkTest extends BaseSelenideTest {
    @Test
    @DisplayName("3.1 Оплата трёх единиц товара на сумму не более 300 рублей")
    void threeUnitsAreAcceptedForProcessing() {
        String name = data.newName();
        long id = data.create(name, 100); // Данные создаём запросом POST /goods/add.
        refresh();
        $("#q-" + id).setValue("3");
        $("#card-" + id + " [data-action='add-to-cart']").click();
        $("#open-cart-btn").click();
        $("#cart-item-" + id + " b").shouldBe(visible, exactText(name));
        $("#cart-item-" + id + " .qty-controls span").shouldBe(exactText("3"));
        $("#total-price").shouldBe(exactText("300"));
        $("#makeOrder").click();
        $$(".toast").findBy(exactText("Заказ принят в обработку!"))
                .shouldBe(visible, exactText("Заказ принят в обработку!"));
        $("#main-title").shouldBe(exactText("Заказ успешно оформлен!"));
        $("#cart-count").shouldBe(exactText("0"));
        $("#open-cart-btn").click();
        $("#empty-cart").shouldBe(visible, exactText("Пусто"));
        $("#total-price").shouldBe(exactText("0"));
    }

    @Test
    @DisplayName("3.2 Общая цена нескольких разных товаров рассчитана корректно")
    void differentProductsHaveCorrectTotal() {
        String first = data.newName();
        String second = data.newName();
        String third = data.newName();
        int firstPrice = 35;
        int secondPrice = 60;
        int thirdPrice = 90;
        int firstQty = 2;
        int secondQty = 1;
        int thirdQty = 1;
        long firstId = data.create(first, firstPrice);
        long secondId = data.create(second, secondPrice);
        long thirdId = data.create(third, thirdPrice);
        refresh();
        $("#q-" + firstId).setValue(String.valueOf(firstQty));
        $("#card-" + firstId + " [data-action='add-to-cart']").click();
        $("#q-" + secondId).setValue(String.valueOf(secondQty));
        $("#card-" + secondId + " [data-action='add-to-cart']").click();
        $("#q-" + thirdId).setValue(String.valueOf(thirdQty));
        $("#card-" + thirdId + " [data-action='add-to-cart']").click();
        $("#open-cart-btn").click();
        $("#cart-item-" + firstId + " b").shouldBe(visible, exactText(first));
        $("#cart-item-" + secondId + " b").shouldBe(visible, exactText(second));
        $("#cart-item-" + thirdId + " b").shouldBe(visible, exactText(third));
        $("#cart-item-" + firstId + " .qty-controls span").shouldBe(exactText("2"));
        $("#cart-item-" + secondId + " .qty-controls span").shouldBe(exactText("1"));
        $("#cart-item-" + thirdId + " .qty-controls span").shouldBe(exactText("1"));
        int expectedTotal = firstPrice * firstQty + secondPrice * secondQty + thirdPrice * thirdQty;
        $("#total-price").shouldBe(exactText(String.valueOf(expectedTotal)));
        $("#cart-count").shouldBe(exactText(String.valueOf(firstQty + secondQty + thirdQty)));
    }

    @Test
    @DisplayName("3.3 После добавления товара в админке появляется уведомление")
    void adminProductCreationShowsNotification() {
        String name = data.newName();
        $("a[href='/admin']").click();
        $("[name='username']").setValue(UiSettings.USERNAME);
        $("[name='password']").setValue(UiSettings.password());
        $("button[type='submit']").click();
        $("#n-name").shouldBe(visible).setValue(name);
        $("#n-price").setValue("95");
        $("#add-btn").click();
        $(".toast").shouldBe(visible, exactText("Товар успешно добавлен!"));
        $("#tbody input[value='" + name + "']").shouldBe(visible, exactValue(name));
        $("#n-name").shouldBe(exactValue(""));
        $("#n-price").shouldBe(exactValue(""));
    }

    @Test
    @DisplayName("3.4 Изменение имени и цены сохраняется в списке товаров")
    void adminEditedProductIsPersisted() {
        String original = data.newName();
        long id = data.create(original, 50);
        String edited = data.newName();
        $("a[href='/admin']").click();
        $("[name='username']").setValue(UiSettings.USERNAME);
        $("[name='password']").setValue(UiSettings.password());
        $("button[type='submit']").click();
        $("#nm-" + id).shouldBe(visible, exactValue(original)).setValue(edited);
        $("#pr-" + id).setValue("85");
        $("[data-action='update'][data-id='" + id + "']").click();
        $(".toast").shouldBe(visible, exactText("Товар #" + id + " обновлен"));
        // Уходим со страницы редактирования и заново загружаем список с сервера.
        $("a[href='/']").click();
        $("a[href='/admin']").click();
        $("#nm-" + id).shouldBe(visible, exactValue(edited));
        $("#pr-" + id).shouldBe(exactValue("85"));
        $("a[href='/']").click();
        $("#card-" + id + " h4").shouldBe(visible, exactText(edited));
        $("#card-" + id).shouldBe(attribute("data-price", "85"));
    }
}
