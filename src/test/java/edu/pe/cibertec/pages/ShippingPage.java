package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShippingPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By addressFieldBy = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.EditText\").instance(0)"
    );

    private final By confirmBtnBy = AppiumBy.androidUIAutomator(
            "new UiSelector().classNameMatches(\"android\\\\.widget\\\\.(Button|TextView)\")" +
                    ".textMatches(\"(?i).*(confirmar|finalizar|comprar|realizar compra|pagar|continuar|siguiente).*\")"
    );

    private final By addressRequiredMsgBy = AppiumBy.androidUIAutomator(
            "new UiSelector().textMatches(\"(?i).*(direcci[oó]n|direccion).*(requerida|obligatoria|debe|ingrese|ingresa).*\")"
    );

    public ShippingPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    private boolean exists(By by){
        try {
            return !driver.findElements(by).isEmpty();
        } catch (Exception e){
            return false;
        }
    }

    private WebElement waitClickable(By by){
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void enterAddress(String address){
        WebElement field = waitClickable(addressFieldBy);
        field.click();
        field.clear();
        if (address != null) field.sendKeys(address);
        try { driver.hideKeyboard(); } catch (Exception ignored) {}
    }

    public void confirmPurchase(){
        if (exists(confirmBtnBy)) {
            waitClickable(confirmBtnBy).click();
        }
    }

    public boolean isAddressRequiredMessageVisible(){
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(addressRequiredMsgBy)).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }
}
