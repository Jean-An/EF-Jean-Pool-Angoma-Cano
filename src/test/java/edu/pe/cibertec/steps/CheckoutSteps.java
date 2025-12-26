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

    @Before
    public void setUp(){
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        shippingPage = new ShippingPage(driver);
    }

    @After
    public void tearDown(){
        AppiumConfig.quitDriver();
    }

    private void loginIfNeeded(){
        if (!homePage.isHomePageDisplayed()) {
            loginPage.login("admin@test.com", "123456");
            Assertions.assertTrue(homePage.isHomePageDisplayed(), "No se pudo hacer login / no se mostró Home");
        }
    }

    @Given("que el usuario tiene productos en el carrito")
    public void usuarioConProductosEnCarrito(){
        loginIfNeeded();

        homePage.clickProduct("Laptop HP Pavilion");
        Assertions.assertTrue(productDetailPage.isProductDetailDisplayed(), "No entró a detalle del producto");

        productDetailPage.ClickAddToCart();
        Assertions.assertTrue(productDetailPage.isProductAddedMessageDisplayed(), "No se mostró confirmación de agregado");

        productDetailPage.goBack();
        Assertions.assertTrue(homePage.isHomePageDisplayed(), "No regresó a Home");

        homePage.clickCartTab();
        Assertions.assertTrue(cartPage.isCartDisplayed(), "No se abrió carrito");
        Assertions.assertFalse(cartPage.isCartEmpty(), "El carrito aparece vacío pero se agregó un producto");
    }

    @When("procede al checkout")
    public void procedeCheckout(){
        checkoutPage.clickCheckout();
    }

    @And("ingresa los datos de envio")
    public void ingresaDatosEnvio(){
        shippingPage.enterAddress("Av. Test 123");
    }

    @And("no ingresa direccion de envio")
    public void noIngresaDireccion(){
        shippingPage.enterAddress("");
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
        loginIfNeeded();

        homePage.clickCartTab();
        Assertions.assertTrue(cartPage.isCartDisplayed(), "No se abrió carrito");
        Assertions.assertTrue(cartPage.isCartEmpty(), "El carrito no está vacío");
    }

    @When("intenta proceder al checkout")
    public void intentaCheckout(){
        checkoutPage.clickCheckout();
    }

    @Then("deberia ver mensaje de carrito vacio")
    public void validaCarritoVacio(){
        Assertions.assertTrue(cartPage.isCartEmpty() || checkoutPage.isEmptyCartMessageVisible(),
                "No se mostró mensaje de carrito vacío / no se bloqueó checkout");
    }

    @Then("deberia ver mensaje de direccion requerida")
    public void validaDireccionRequerida(){
        Assertions.assertTrue(shippingPage.isAddressRequiredMessageVisible(),
                "No se mostró validación de dirección requerida");
    }
}
