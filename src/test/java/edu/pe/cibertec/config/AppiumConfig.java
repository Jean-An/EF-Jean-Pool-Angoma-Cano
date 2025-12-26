package edu.pe.cibertec.config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class AppiumConfig {

    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    private static void initializeDriver() {
        String appPath = Paths.get(System.getProperty("user.dir"), "apk", "shooping-cart-appium-demo.apk")
                .toAbsolutePath()
                .toString();

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("R58T10PSX5H")
                .setAutomationName("UiAutomator2")
                .setApp(appPath)
                .setNoReset(false)
                .setAutoGrantPermissions(true)
                .setAppWaitActivity("*")
                .setAppWaitDuration(Duration.ofSeconds(60))
                .setAdbExecTimeout(Duration.ofSeconds(120))
                .setUiautomator2ServerInstallTimeout(Duration.ofSeconds(120))
                .setNewCommandTimeout(Duration.ofSeconds(120));

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL Appium inválida", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
