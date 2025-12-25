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

    @Before("@logout")
    public void setUp(){
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);
    }

    @After("@logout")
    public void tearDown(){
        AppiumConfig.quitDriver();
    }

    @Given("que el usuario esta logueado en la aplicacion")
    public void usuarioLogueado(){
        loginPage.login("admin@test.com", "123456");
        Assertions.assertTrue(homePage.isHomePageDisplayed(), "No se pudo loguear");
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
        // Lo más robusto: verificar que el botón login existe nuevamente
        // (crea un método en LoginPage para detectar botón o pantalla)
        Assertions.assertDoesNotThrow(() -> loginPage.clickLoginButton(),
                "No parece estar en login (no se pudo interactuar con botón login)");
    }
}
