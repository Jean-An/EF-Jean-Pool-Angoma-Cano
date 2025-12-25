package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.*;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class CheckoutSteps {

    private AndroidDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private ShippingPage shippingPage;

    @Before("@checkout")
    public void setUp(){
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        shippingPage = new ShippingPage(driver);
    }

    @After("@checkout")
    public void tearDown(){
        AppiumConfig.quitDriver();
    }

    @Given("que el usuario tiene productos en el carrito")
    public void usuarioConProductosEnCarrito(){
        loginPage.login("admin@test.com", "123456");
        Assertions.assertTrue(homePage.isHomePageDisplayed(), "No se pudo hacer login/home");

        homePage.clickProduct("Laptop HP Pavilion");
        Assertions.assertTrue(productDetailPage.isProductDetailDisplayed(), "No entró a detalle");
        productDetailPage.ClickAddToCart();
        Assertions.assertTrue(productDetailPage.isProductAddedMessageDisplayed(), "No se mostró confirmación de agregado");

        productDetailPage.goBack();
        homePage.clickCartTab();
        Assertions.assertTrue(cartPage.isCartDisplayed(), "No se abrió carrito");
    }

    @When("procede al checkout")
    public void procedeCheckout(){
        checkoutPage.clickCheckout();
    }

    @And("ingresa los datos de envio")
    public void ingresaDatosEnvio(){
        shippingPage.enterAddress("Av. Test 123");
    }

    @And("confirma la compra")
    public void confirmaCompra(){
        shippingPage.confirmPurchase();
    }

    @Then("deberia ver el mensaje de compra exitosa")
    public void validaCompraExitosa(){
        Assertions.assertTrue(checkoutPage.isSuccessMessageVisible(), "No se mostró mensaje de compra exitosa");
    }

    @Given("que el usuario tiene el carrito vacio")
    public void usuarioCarritoVacio(){
        loginPage.login("admin@test.com", "123456");
        homePage.clickCartTab();
        Assertions.assertTrue(cartPage.isCartDisplayed(), "No se abrió carrito");
        Assertions.assertTrue(cartPage.isCartEmpty(), "El carrito no está vacío (debe iniciar vacío)");
    }

    @When("intenta proceder al checkout")
    public void intentaCheckout(){
        checkoutPage.clickCheckout();
    }

    @Then("deberia ver mensaje de carrito vacio")
    public void validaCarritoVacio(){
        Assertions.assertTrue(cartPage.isCartEmpty(), "No se mostró mensaje de carrito vacío");
    }

    @And("no ingresa direccion de envio")
    public void noIngresaDireccion(){
        shippingPage.enterAddress(""); // o simplemente no escribir nada si el campo inicia vacío
    }

    @Then("deberia ver mensaje de direccion requerida")
    public void validaDireccionRequerida(){
        Assertions.assertTrue(shippingPage.isAddressRequiredMessageVisible(),
                "No se mostró validación de dirección requerida");
    }
}
