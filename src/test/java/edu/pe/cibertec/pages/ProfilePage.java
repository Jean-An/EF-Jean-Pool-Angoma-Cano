package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ProfilePage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ProfilePage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProfileDisplayed(){
        try{
            WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.TextView[contains(@text,'Perfil')]")
            ));
            return titulo.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public void openUserMenu(){
        // Ajusta locator real (menu / opciones)
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[contains(@text,'Menu') or contains(@text,'Opciones')]")
        )).click();
    }

    public void clickLogout(){
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[contains(@text,'Cerrar sesion') or contains(@text,'Cerrar sesión')]")
        )).click();
    }
}
