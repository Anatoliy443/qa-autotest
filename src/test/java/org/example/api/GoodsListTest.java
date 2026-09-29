package org.example.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.api.RestApiBuilder.request;
import static org.hamcrest.Matchers.*;

class GoodsListTest extends BaseApiTest {
    @Test
    void emptyListUsingGivenWhenThen() {
        given().spec(request())
                .when().get(Urls.LIST)
                .then().statusCode(200).body("goods", empty());
    }

    @Test
    void emptyListUsingRequestSpecification() {
        RequestSpecification specification = request();
        Response response = specification.get(Urls.LIST);
        response.then().statusCode(200).body("goods", empty());
    }

    @Test
    void createdGoodIsListedUsingRestAssured() {
        String name = uniqueName();
        long id = createGood(name, 123.45);
        request().get(Urls.LIST).then().statusCode(200)
                .body("goods", hasSize(1))
                .body("goods[0].id", equalTo((int) id))
                .body("goods[0].name", equalTo(name))
                .body("goods[0].price", equalTo(123.45f));
    }

    @Test
    void createdGoodIsListedUsingAssertJ() {
        String name = uniqueName();
        long id = createGood(name, 250.50);
        Response response = request().get(Urls.LIST);
        assertThat(response.statusCode()).as("GET /goods/list: код ответа").isEqualTo(200);
        List<Map<String, Object>> goods = response.jsonPath().getList("goods");
        assertThat(goods).as("В списке должен быть ровно один созданный товар").hasSize(1);
        Map<String, Object> good = goods.get(0);
        assertThat(((Number) good.get("id")).longValue()).isEqualTo(id);
        assertThat(good.get("name")).isEqualTo(name);
        assertThat(((Number) good.get("price")).doubleValue()).isEqualTo(250.50);
    }

    @Test
    void paginationReturnsDifferentGoodsOnAdjacentPages() {
        long first = createGood(uniqueName(), 10);
        long second = createGood(uniqueName(), 20);
        Response page0 = request().queryParam("page", 0).queryParam("size", 1).get(Urls.LIST);
        Response page1 = request().queryParam("page", 1).queryParam("size", 1).get(Urls.LIST);
        page0.then().statusCode(200).body("goods", hasSize(1));
        page1.then().statusCode(200).body("goods", hasSize(1));
        List<Long> ids = List.of(((Number) page0.path("goods[0].id")).longValue(),
                ((Number) page1.path("goods[0].id")).longValue());
        assertThat(ids).as("Соседние страницы вместе содержат оба товара без повторений")
                .containsExactlyInAnyOrder(first, second);
    }
}
