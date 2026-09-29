package org.example.api;

import org.junit.jupiter.api.Test;

import static org.example.api.RestApiBuilder.request;
import static org.hamcrest.Matchers.*;

class GoodsGetTest extends BaseApiTest {
    @Test
    void existingGoodReturns200() {
        String name = uniqueName();
        long id = createGood(name, 42.50);
        request().pathParam("id", id).get(Urls.GOOD).then().statusCode(200)
                .body("id", equalTo((int) id)).body("name", equalTo(name))
                .body("price", equalTo(42.50f));
    }

    @Test
    void missingGoodReturns404() {
        request().pathParam("id", MISSING_ID).get(Urls.GOOD).then().statusCode(404);
    }
}
