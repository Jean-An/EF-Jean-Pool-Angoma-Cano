package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.*;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class LogoutSteps {

    private AndroidDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ProfilePage profilePage;

    @Before
    public void setUp(){
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);
    }

    @After
    public void tearDown(){
        AppiumConfig.quitDriver();
    }

    @When("hace clic en el menu de usuario")
    public void clickMenuUsuario(){
        homePage.clickProfileTab();
        Assertions.assertTrue(profilePage.isProfileDisplayed(), "No se abrió la pantalla Perfil");
        profilePage.openUserMenu();
    }

    @And("hace clic en cerrar sesion")
    public void clickCerrarSesion(){
        profilePage.clickLogout();
    }

    @Then("deberia regresar a la pantalla de login")
    public void validaRegresoLogin(){
        Assertions.assertTrue(loginPage.isLoginScreenDisplayed(),
                "No regresó a Login (no se detectan campos/botón)");
    }
}
