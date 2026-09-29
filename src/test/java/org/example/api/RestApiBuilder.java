package org.example.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/** Единая точка настройки запросов; новый экземпляр для каждого запроса. */
public final class RestApiBuilder {
    private RestApiBuilder() { }

    public static RequestSpecification request() {
        String username = setting("api.username", "API_USERNAME", "admin");
        String password = setting("api.password", "API_PASSWORD", null);
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("Задайте API_PASSWORD или -Dapi.password для учебного API");
        }
        return given().spec(new RequestSpecBuilder()
                .setBaseUri(setting("api.baseUrl", "API_BASE_URL", "http://127.0.0.1:8080"))
                .setContentType(ContentType.JSON)
                .build())
                .auth().preemptive().basic(username, password);
    }

    private static String setting(String property, String environment, String fallback) {
        return System.getProperty(property, System.getenv().getOrDefault(environment, fallback));
    }
}
