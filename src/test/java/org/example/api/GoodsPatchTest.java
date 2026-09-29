package org.example.api;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.example.api.RestApiBuilder.request;
import static org.hamcrest.Matchers.*;

class GoodsPatchTest extends BaseApiTest {
    @Test
    void updateReturns200AndPersistsBothFields() {
        long id = createGood(uniqueName(), 10);
        String updatedName = uniqueName();
        request().pathParam("id", id).body(Map.of("name", updatedName, "price", 25.50))
                .patch(Urls.GOOD).then().statusCode(200);
        request().pathParam("id", id).get(Urls.GOOD).then().statusCode(200)
                .body("name", equalTo(updatedName)).body("price", equalTo(25.50f));
    }

    @Test
    void duplicateNameReturns400AndDoesNotChangeGood() {
        String takenName = uniqueName();
        createGood(takenName, 10);
        String name = uniqueName();
        long id = createGood(name, 20);
        request().pathParam("id", id).body(Map.of("name", takenName, "price", 30))
                .patch(Urls.GOOD).then().statusCode(400);
        request().pathParam("id", id).get(Urls.GOOD).then().statusCode(200)
                .body("name", equalTo(name)).body("price", equalTo(20f));
    }

    @Test
    void missingGoodReturns404() {
        request().pathParam("id", MISSING_ID).body(Map.of("name", uniqueName(), "price", 10))
                .patch(Urls.GOOD).then().statusCode(404);
    }

    @Test
    void negativePriceReturns400AndDoesNotChangeGood() {
        String name = uniqueName();
        long id = createGood(name, 20);
        request().pathParam("id", id).body(Map.of("name", name, "price", -1))
                .patch(Urls.GOOD).then().statusCode(400);
        request().pathParam("id", id).get(Urls.GOOD).then().statusCode(200)
                .body("price", equalTo(20f));
    }

    @Test
    void patchOnlyNameKeepsPrice() {
        long id = createGood(uniqueName(), 20);
        String name = uniqueName();
        request().pathParam("id", id).body(Map.of("name", name))
                .patch(Urls.GOOD).then().statusCode(200);
        request().pathParam("id", id).get(Urls.GOOD).then().statusCode(200)
                .body("name", equalTo(name)).body("price", equalTo(20f));
    }

    @Test
    void missingRequiredNameReturns400AndDoesNotChangeGood() {
        String name = uniqueName();
        long id = createGood(name, 20);
        request().pathParam("id", id).body(Map.of("price", 30))
                .patch(Urls.GOOD).then().statusCode(400);
        request().pathParam("id", id).get(Urls.GOOD).then().statusCode(200)
                .body("name", equalTo(name)).body("price", equalTo(20f));
    }
}
