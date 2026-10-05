package org.example.ui;

import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import static io.restassured.RestAssured.given;

/** Только API и учёт данных; браузерные действия остаются в тестовых классах. */
final class UiTestData {
    private final Set<String> names = new LinkedHashSet<>();
    private final Set<Long> ids = new LinkedHashSet<>();

    String newName() {
        String name = "hw6-" + UUID.randomUUID();
        names.add(name); // До UI-запроса: очистка сработает и после упавшего теста.
        return name;
    }

    long create(String name, int price) {
        names.add(name);
        Response response = given().baseUri(UiSettings.BASE_URL)
                .auth().preemptive().basic(UiSettings.USERNAME, UiSettings.password())
                .contentType("application/json").body(Map.of("name", name, "price", price))
                .post("/goods/add");
        Number id = response.path("data.id");
        if (id != null) ids.add(id.longValue());
        if (response.statusCode() != 200 || id == null) {
            throw new IllegalStateException("Не удалось подготовить товар: HTTP " + response.statusCode());
        }
        return id.longValue();
    }

    void deleteCreatedGoods() {
        List<String> errors = new ArrayList<>();
        // UI создаёт товар без возврата ID в браузер: находим только свои уникальные имена.
        if (!names.isEmpty()) {
            try {
                int page = 0;
                while (true) {
                    Response response = given().baseUri(UiSettings.BASE_URL)
                            .auth().preemptive().basic(UiSettings.USERNAME, UiSettings.password())
                            .queryParam("page", page++).queryParam("size", 100).get("/goods/list");
                    if (response.statusCode() != 200) throw new IllegalStateException("HTTP " + response.statusCode());
                    List<Map<String, Object>> goods = response.path("goods");
                    for (Map<String, Object> good : goods) {
                        if (names.contains(good.get("name"))) ids.add(((Number) good.get("id")).longValue());
                    }
                    if (goods.size() < 100) break;
                }
            } catch (RuntimeException error) {
                errors.add("Поиск созданных через UI товаров: " + error.getMessage());
            }
        }
        for (long id : ids) {
            try {
                int status = given().baseUri(UiSettings.BASE_URL)
                        .auth().preemptive().basic(UiSettings.USERNAME, UiSettings.password())
                        .delete("/goods/" + id).statusCode();
                if (status != 200 && status != 404) errors.add(id + ": HTTP " + status);
            } catch (RuntimeException error) {
                errors.add(id + ": " + error.getMessage());
            }
        }
        if (!errors.isEmpty()) throw new IllegalStateException("Очистка тестовых данных: " + errors);
    }
}
