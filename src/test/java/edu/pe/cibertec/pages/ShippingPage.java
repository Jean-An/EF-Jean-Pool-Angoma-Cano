package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ShippingPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ShippingPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterAddress(String address){
        // Ajusta el selector: ideal con resource-id
        WebElement addressField = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)")
        ));
        addressField.click();
        addressField.clear();
        addressField.sendKeys(address);
    }

    public void confirmPurchase(){
        // CAMBIA por locator real
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[contains(@text,'Confirmar')]")
        )).click();
    }

    public boolean isAddressRequiredMessageVisible(){
        // CAMBIA por mensaje real
        try{
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.TextView[contains(@text,'dirección') or contains(@text,'direccion')]")
            ));
            return msg.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }
}
