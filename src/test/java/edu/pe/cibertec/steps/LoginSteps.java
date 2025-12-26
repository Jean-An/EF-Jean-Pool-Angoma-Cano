package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class LoginSteps {

    private AndroidDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @Before("@login")
    public void setUp(){
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @After("@login")
    public void tearDown(){
        AppiumConfig.quitDriver();
    }

    private boolean isHomeMarkerPresent(){
        return !driver.findElements(
                AppiumBy.xpath("//android.widget.TextView[@text='Productos']")
        ).isEmpty();
    }

    @Given("que el usuario esta en la pantalla de login")
    public void enLogin(){
        System.out.println("=== PAGE SOURCE (LOGIN) ===");
        System.out.println(driver.getPageSource());

        Assertions.assertTrue(loginPage.isLoginScreenDisplayed(), "No se muestra la pantalla de Login");
        Assertions.assertFalse(isHomeMarkerPresent(), "Ya está en Home y debería estar en Login");
    }

    @When("ingresa el email {string}")
    public void ingresaEmail(String email){
        loginPage.enterEmail(email);
    }

    @And("ingresa el password {string}")
    public void ingresaPassword(String password){
        loginPage.enterPassword(password);
    }

    @And("hacer clic en el boton login")
    public void clickLogin(){
        loginPage.clickLoginButton();
    }

    @Then("deberia acceder a la pantalla principal")
    public void validaHome(){
        Assertions.assertTrue(homePage.isHomePageDisplayed(), "No se mostró Home/Productos (login falló)");
    }

    @Then("deberia ver un mensaje de error")
    public void validaError(){
        Assertions.assertTrue(loginPage.isLoginScreenDisplayed(), "Tras login fallido, no se mantuvo en Login");
        Assertions.assertFalse(isHomeMarkerPresent(), "Entró al Home cuando el login debía fallar");
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(), "No se mostró mensaje de error en login fallido");
    }
}
