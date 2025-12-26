package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public LoginPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    private WebElement waitVisible(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private WebElement tryWaitClickable(By by, int seconds){
        try{
            return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException e){
            return null;
        }
    }

    private WebElement tryWaitVisible(By by, int seconds){
        try{
            return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e){
            return null;
        }
    }

    private final By emailBy = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.EditText\").instance(0)"
    );

    private final By passwordBy = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.EditText\").instance(1)"
    );

    private final By loginTapAreaBy = AppiumBy.xpath(
            "//android.widget.TextView[@text='Iniciar Sesión' or @text='Iniciar Sesion']" +
                    "/ancestor::android.view.View[@clickable='true'][1]"
    );

    private WebElement getEmailField(){
        return waitVisible(emailBy);
    }

    private WebElement getPasswordField(){
        return waitVisible(passwordBy);
    }

    private WebElement getLoginButton(){
        WebElement btn = tryWaitClickable(loginTapAreaBy, 2);
        if (btn != null) return btn;

        btn = tryWaitVisible(loginTapAreaBy, 2);
        if (btn != null) return btn;

        return new WebDriverWait(driver, Duration.ofSeconds(12))
                .until(ExpectedConditions.presenceOfElementLocated(loginTapAreaBy));
    }

    public void enterEmail(String email){
        WebElement el = getEmailField();
        el.click();
        el.clear();
        el.sendKeys(email);
    }

    public void enterPassword(String password){
        WebElement el = getPasswordField();
        el.click();
        el.clear();
        el.sendKeys(password);
    }

    public void clickLoginButton (){
        getLoginButton().click();
    }

    public void login(String email, String password){
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginScreenDisplayed(){
        WebElement email = tryWaitVisible(emailBy, 6);
        WebElement pass  = tryWaitVisible(passwordBy, 6);
        if (email == null || pass == null) return false;

        return tryWaitVisible(loginTapAreaBy, 2) != null || tryWaitClickable(loginTapAreaBy, 2) != null;
    }

    public boolean isErrorMessageDisplayed() {
        By toastBy = By.xpath("//android.widget.Toast");
        By errorTextBy = AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.TextView\").textMatches(\"(?i).*(error|incorrect|inv[aá]lid|credenciales|usuario|contrase[nñ]a|fall[oó]).*\")"
        );

        try {
            if (!driver.findElements(toastBy).isEmpty()) return true;
        } catch (Exception ignored) {}

        try {
            return !driver.findElements(errorTextBy).isEmpty();
        } catch (Exception ignored) {
            return false;
        }
    }
}
