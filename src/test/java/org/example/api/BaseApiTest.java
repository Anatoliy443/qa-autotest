package org.example.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.api.RestApiBuilder.request;

@Tag("api")
abstract class BaseApiTest {
    protected static final long MISSING_ID = Long.MAX_VALUE;
    private final List<Long> createdIds = new ArrayList<>();

    protected String uniqueName() {
        return "hw4-" + UUID.randomUUID();
    }

    // Запоминаем ID до проверки ответа: даже при дефекте ассерта товар будет удалён.
    protected Response add(String name, double price) {
        return addBody(Map.of("name", name, "price", price));
    }

    protected Response addBody(Object body) {
        Response response = request().body(body).post(Urls.ADD);
        if (response.statusCode() == 200) {
            Number id = response.path("data.id");
            if (id != null) createdIds.add(id.longValue());
        }
        return response;
    }

    protected long createGood(String name, double price) {
        Response response = add(name, price);
        response.then().statusCode(200);
        Number id = response.path("data.id");
        assertThat(id).as("POST /goods/add должен вернуть data.id").isNotNull();
        assertThat(id.longValue()).isPositive();
        return id.longValue();
    }

    @AfterEach
    void deleteCreatedGoods() {
        // Удаляем только данные этого теста, в том числе после упавших проверок.
        List<String> errors = new ArrayList<>();
        for (long id : createdIds) {
            try {
                Response response = request().pathParam("id", id).delete(Urls.GOOD);
                if (response.statusCode() != 200 && response.statusCode() != 404) {
                    errors.add(id + ": HTTP " + response.statusCode() + " " + response.asString());
                }
            } catch (RuntimeException exception) {
                errors.add(id + ": " + exception.getMessage());
            }
        }
        assertThat(errors).as("Очистка созданных тестом товаров").isEmpty();
    }
}
