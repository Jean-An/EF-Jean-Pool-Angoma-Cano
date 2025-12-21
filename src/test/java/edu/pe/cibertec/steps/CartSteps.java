package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.CartPage;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import edu.pe.cibertec.pages.ProductDetailPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class CartSteps {

    private static AndroidDriver driver;
    private static LoginPage loginPage;
    private static HomePage homePage;
    private static ProductDetailPage productDetailPage;
    private static CartPage cartPage;

    @Before("@cart")
    public void setUp(){
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
    }

    @After("@cart")
    public void tearDown(){
        AppiumConfig.quitDriver();

    }

    @Given("el usuario inicia sesion con credenciales validas")
    public void elUsuarioInicioSesionConCreedencialesValidad(){
        loginPage.enterEmail("admin@test.com");
        loginPage.enterPassword("123456");
        loginPage.clickLoginButton();

        boolean loginExitoso = homePage.isHomePageDisplayed();

        Assertions.assertTrue(loginExitoso,"ERROR: no se pudo hacer login");

    }


    @Given("el usuario esta en la pantalla principal")
    public void elUsuarioEstaEnLaPantallaPrincipal(){
        boolean enHome = homePage.isHomePageDisplayed();
        Assertions.assertTrue(enHome, "ERROR: NO estamos en la pantalla principal");
    }

    @When("el usuario selecciona el producto {string}")
    public void elUsuarioSeleccionElProducto(String productName){
        homePage.clickProduct(productName);
    }

    @And("el usuario hace clic en agregar al carrito")
    public  void elUsuarioHaceClickEnAgregarCarrito(){
        productDetailPage.ClickAddToCart();
    }

    @And("el usuario regresa a la pantalla principal")
    public void elUsuarioRegresaALaPantallaPrincipal(){
        productDetailPage.goBack();
        boolean enHome = homePage.isHomePageDisplayed();
        Assertions.assertTrue(enHome, "Error: No pudimos regresar al home");
    }

    @When("el usuario navega al carrito")
    public void elUsuarioNavegaAlCarrito(){
        homePage.clickCartTab();

        Assertions.assertTrue(cartPage.isCartDisplayed(), "Error: No se pudo abrir carrito");
    }

    @Then("el usuario deberia ver el mensaje de confirmacion")
    public void elUsuarioDeberiaVerElMensajeDeConfirmacion(){
        boolean isMessageVisible = productDetailPage.isProductAddedMessageDisplayed();
        Assertions.assertTrue(isMessageVisible, "Error: NO se mostro mensaje!!");
    }

    @Then("el usuario deberia ver el producto {string} en el carrito")
    public void elUsuarioDeberiaVerElProductoEnElCarrito(String productName){
        boolean productoEnCarrrito = cartPage.isProductInCart(productName);
        Assertions.assertTrue(productoEnCarrrito, "ERROR: EL PRODUCTO NO ESTA EN EL CARRITO");
    }

    @Then("el usuario deberia ver el mensaje de carrito vacio")
    public void elUsuarioDeberiaVerElMensajeDeCarritoVacio(){
        boolean carritoVacio = cartPage.isCartEmpty();
        Assertions.assertTrue(carritoVacio, "Carrito no esta vacio");
    }



}
