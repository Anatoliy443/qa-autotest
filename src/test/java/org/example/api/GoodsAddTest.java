package org.example.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.example.api.RestApiBuilder.request;
import static org.hamcrest.Matchers.*;

class GoodsAddTest extends BaseApiTest {
    @Test
    void addReturns200AndPersistsGood() {
        String name = uniqueName();
        Response response = add(name, 99.99);
        response.then().statusCode(200).body("message", equalTo("success"))
                .body("data.id", notNullValue());
        request().pathParam("id", response.path("data.id")).get(Urls.GOOD).then()
                .statusCode(200).body("name", equalTo(name)).body("price", equalTo(99.99f));
    }

    @Test
    void duplicateNameReturns400() {
        String name = uniqueName();
        createGood(name, 10);
        add(name, 20).then().statusCode(400);
    }

    @ParameterizedTest(name = "Некорректное тело POST: {0}")
    @ValueSource(strings = {"{\"price\":10}", "{\"name\":\"\",\"price\":10}",
            "{\"name\":\"   \",\"price\":10}", "{", "{}"})
    void invalidBodyReturns400(String body) {
        addBody(body).then().statusCode(400);
    }

    @Test
    void negativePriceReturns400() {
        add(uniqueName(), -1).then().statusCode(400);
    }

    @Test
    void zeroPriceIsAccepted() {
        String name = uniqueName();
        long id = createGood(name, 0);
        request().pathParam("id", id).get(Urls.GOOD).then()
                .statusCode(200).body("price", equalTo(0f));
    }
}
