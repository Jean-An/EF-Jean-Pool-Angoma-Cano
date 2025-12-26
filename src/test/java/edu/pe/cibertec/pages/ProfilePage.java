package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Collections;

public class ProfilePage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ProfilePage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isProfileDisplayed(){
        try{
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.TextView[contains(@text,'Perfil')]")
            )).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    private void tap(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence seq = new Sequence(finger, 1);
        seq.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        seq.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        seq.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(seq));
    }

    public void openUserMenu(){
        // 1) Lo más común en Android: “Más opciones”
        By[] candidates = new By[] {
                AppiumBy.accessibilityId("Más opciones"),
                AppiumBy.accessibilityId("More options"),
                AppiumBy.accessibilityId("Menu"),
                AppiumBy.xpath("//*[@content-desc='Más opciones' or @content-desc='More options' or @content-desc='Menu']")
        };

        for (By by : candidates){
            try {
                wait.until(ExpectedConditions.elementToBeClickable(by)).click();
                return;
            } catch (Exception ignored) {}
        }

        // 2) Fallback por texto (si de verdad existe)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.xpath("//android.widget.TextView[contains(@text,'Menu') or contains(@text,'Opciones')]")
            )).click();
            return;
        } catch (Exception ignored) {}

        // 3) Último fallback: tap esquina superior derecha (menú de 3 puntos)
        Dimension size = driver.manage().window().getSize();
        int x = size.width - 40;
        int y = 80;
        tap(x, y);
    }

    public void clickLogout(){
        // soporta “Cerrar sesión”, “Cerrar sesion”, “Logout”
        By logoutBy = AppiumBy.androidUIAutomator(
                "new UiSelector().textMatches(\"(?i).*(cerrar\\s+ses[ií]on|logout|salir).*\")"
        );
        wait.until(ExpectedConditions.elementToBeClickable(logoutBy)).click();
    }
}
