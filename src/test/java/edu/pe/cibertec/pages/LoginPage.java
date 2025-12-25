package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private AndroidDriver driver;

    public LoginPage(AndroidDriver driver){
        this.driver = driver;
    }
    // LOCALIZADORES DE PANTALLA
    private WebElement getEmailField(){
        return  driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(0)"
                )
        );
    }

    private WebElement getPasswordField(){
        return  driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(1)"
                )
        );
    }

    private WebElement getLoginButton(){
        return  driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.Button\").instance(1)"
                )
        );
    }

    // ACCIONES DE LOS ELEMENTOS

    public void enterEmail(String email){
        getEmailField().click();
        getEmailField().clear();
        getEmailField().sendKeys(email);
    }

    public void enterPassword(String password)
    {
        getPasswordField().click();
        getPasswordField().clear();
        getPasswordField().sendKeys(password);
    }

    public void clickLoginButton (){
        getLoginButton().click();
    }

    public void login(String email, String password){
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }



}
