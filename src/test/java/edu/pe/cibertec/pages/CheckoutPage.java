package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickCheckout(){
        // CAMBIA este locator por el real
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[contains(@text,'Checkout') or contains(@text,'Pagar') or contains(@text,'Comprar')]")
        )).click();
    }

    public boolean isSuccessMessageVisible(){
        // CAMBIA por el texto real
        try{
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.TextView[contains(@text,'exitosa') or contains(@text,'éxito')]")
            ));
            return msg.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }
}
