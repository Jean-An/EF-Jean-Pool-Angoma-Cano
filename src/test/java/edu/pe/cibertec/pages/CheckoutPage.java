package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Botones típicos
    private final By successMsgBy = AppiumBy.androidUIAutomator(
            "new UiSelector().textMatches(\"(?i).*(compra.*exitosa|pedido.*confirmado|gracias.*compra|success|éxito|exito).*\")"
    );

    private final By emptyCartMsgBy = AppiumBy.androidUIAutomator(
            "new UiSelector().textMatches(\"(?i).*(carrito.*vac[ií]o|tu carrito.*vac[ií]o|no.*productos.*carrito).*\")"
    );

    private final By direccionRequeridaBy = AppiumBy.androidUIAutomator(
            "new UiSelector().textMatches(\"(?i).*(direcci[oó]n.*requerida|ingresa.*direcci[oó]n|campo.*obligatorio).*\")"
    );

    public CheckoutPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void scrollToTextContains(String text){
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"
            ));
        } catch (Exception ignored) {}
    }

    private WebElement waitVisible(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private void clickByTextOrClickableParent(String textContains){
        By labelBy = AppiumBy.xpath("//android.widget.TextView[contains(@text,'" + textContains + "')]");
        scrollToTextContains(textContains);

        WebElement label = waitVisible(labelBy);
        WebElement el = label;

        for (int i = 0; i < 6; i++){
            if ("true".equalsIgnoreCase(el.getAttribute("clickable"))){
                el.click();
                return;
            }
            el = el.findElement(By.xpath(".."));
        }
        label.click();
    }

    /** Paso 1: desde carrito o pantalla previa, entrar a Checkout */
    public void clickCheckout(){
        // NO lo dejes “silencioso”. Si no lo encuentra, que explote con info.
        try {
            try { clickByTextOrClickableParent("Proceder"); return; } catch (Exception ignored) {}
            try { clickByTextOrClickableParent("Checkout"); return; } catch (Exception ignored) {}
            try { clickByTextOrClickableParent("Finalizar"); return; } catch (Exception ignored) {}
            clickByTextOrClickableParent("Pagar");
        } catch (Exception e){
            System.out.println("=== PAGE SOURCE (CHECKOUT - clickCheckout FAIL) ===");
            System.out.println(driver.getPageSource());
            throw e;
        }
    }

    /** Llenar dirección (ajusta el locator si tu app usa otro label) */
    public void typeDireccion(String direccion){
        try {
            // intenta encontrar el campo por el label “Dirección”
            By direccionField = AppiumBy.xpath(
                    "//android.widget.TextView[contains(@text,'Dirección') or contains(@text,'Direccion')]" +
                            "/following::android.widget.EditText[1]"
            );

            scrollToTextContains("Direc");
            WebElement input = waitVisible(direccionField);
            input.click();
            input.clear();
            input.sendKeys(direccion);
        } catch (Exception e){
            System.out.println("=== PAGE SOURCE (CHECKOUT - typeDireccion FAIL) ===");
            System.out.println(driver.getPageSource());
            throw e;
        }
    }

    /** Paso final: confirmar compra */
    public void clickConfirmarCompra(){
        try {
            try { clickByTextOrClickableParent("Confirm"); return; } catch (Exception ignored) {}
            try { clickByTextOrClickableParent("Comprar"); return; } catch (Exception ignored) {}
            clickByTextOrClickableParent("Realizar");
        } catch (Exception e){
            System.out.println("=== PAGE SOURCE (CHECKOUT - clickConfirmarCompra FAIL) ===");
            System.out.println(driver.getPageSource());
            throw e;
        }
    }

    public boolean isSuccessMessageVisible(){
        try {
            return waitVisible(successMsgBy).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isEmptyCartMessageVisible(){
        try {
            return waitVisible(emptyCartMsgBy).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isDireccionRequeridaVisible(){
        try {
            return waitVisible(direccionRequeridaBy).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }
}
