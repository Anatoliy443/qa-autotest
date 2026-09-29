package org.example.api;

import org.junit.jupiter.api.Test;

import static org.example.api.RestApiBuilder.request;
import static org.hamcrest.Matchers.*;

class GoodsDeleteTest extends BaseApiTest {
    @Test
    void existingGoodReturns200AndDisappearsFromList() {
        String name = uniqueName();
        long id = createGood(name, 30);
        request().pathParam("id", id).delete(Urls.GOOD).then().statusCode(200)
                .body(equalTo("Good with id '" + id + "' has been deleted successfully!"));
        request().get(Urls.LIST).then().statusCode(200).body("goods.name", not(hasItem(name)));
    }

    @Test
    void missingGoodReturns404() {
        request().pathParam("id", MISSING_ID).delete(Urls.GOOD).then().statusCode(404);
    }
}
