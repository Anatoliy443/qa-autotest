package org.example.ui;

public final class UiSettings {
    public static final String BASE_URL = setting("ui.baseUrl", "UI_BASE_URL", "http://127.0.0.1:8080").replaceAll("/+$", "");
    public static final String USERNAME = setting("ui.username", "UI_USERNAME", "admin");
    public static final boolean HEADLESS = Boolean.parseBoolean(setting("ui.headless", "UI_HEADLESS", "true"));

    private UiSettings() { }

    public static String password() {
        String value = setting("ui.password", "UI_PASSWORD", System.getenv("API_PASSWORD"));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Задайте UI_PASSWORD (учебный пароль из вебинара)");
        }
        return value;
    }

    private static String setting(String property, String environment, String fallback) {
        return System.getProperty(property, System.getenv().getOrDefault(environment, fallback));
    }
}
