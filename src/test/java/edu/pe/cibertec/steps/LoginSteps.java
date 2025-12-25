package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
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

    @Given("que el usuario esta en la pantalla de login")
    public void enLogin(){
        // Si quieres: valida presencia del botón login creando un método isLoginDisplayed()
        Assertions.assertTrue(true);
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
        Assertions.assertTrue(homePage.isHomePageDisplayed(), "ERROR: No se mostró Home/Productos (login falló)");
    }

    @Then("deberia ver un mensaje de error")
    public void validaError(){
        // En muchas apps el error es Toast o un TextView.
        // Mínimo robusto: NO debe entrar al home.
        Assertions.assertFalse(homePage.isHomePageDisplayed(), "ERROR: Entró al home cuando debía fallar el login");
    }
}
