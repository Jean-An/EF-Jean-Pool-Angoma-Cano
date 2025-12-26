package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.CatalogPage;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class CatalogSteps {

    private AndroidDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private CatalogPage catalogPage;

    @Before("@catalogo or @logout")
    public void setUp() {
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        catalogPage = new CatalogPage(driver);
    }

    @After("@catalogo or @logout")
    public void tearDown() {
        AppiumConfig.quitDriver();
    }

    private void loginIfNeeded() {
        if (!homePage.isHomePageDisplayed()) {
            loginPage.login("admin@test.com", "123456");
            Assertions.assertTrue(homePage.isHomePageDisplayed(), "ERROR: No se pudo iniciar sesión");
        }
    }

    @Given("que el usuario esta logueado en la aplicacion")
    public void usuarioLogueado() {
        loginIfNeeded();
        Assertions.assertTrue(homePage.isHomePageDisplayed(), "ERROR: No se mostró Home/Productos");
    }

    @When("navega al catalogo de productos")
    public void navegaCatalogo() {
        loginIfNeeded();
        Assertions.assertTrue(catalogPage.isCatalogDisplayed(), "ERROR: No estamos en el catálogo (Productos no visible)");
    }

    @Then("deberia ver la lista de productos disponibles")
    public void verLista() {
        Assertions.assertTrue(
                catalogPage.anyVisibleTextContainsIgnoreCase("Laptop")
                        || catalogPage.anyVisibleTextContainsIgnoreCase("Mouse")
                        || catalogPage.anyVisibleTextContainsIgnoreCase("Teclado")
                        || catalogPage.anyVisibleTextContainsIgnoreCase("Audífonos")
                        || catalogPage.anyVisibleTextContainsIgnoreCase("Auriculares"),
                "ERROR: No se detectó ningún producto visible en pantalla"
        );
    }

    @Given("que el usuario esta en el catalogo")
    public void usuarioEnCatalogo() {
        loginIfNeeded();
        Assertions.assertTrue(catalogPage.isCatalogDisplayed(), "ERROR: No estamos en el catálogo");
    }

    @When("busca el producto {string}")
    public void buscaProducto(String nombre) {
        catalogPage.search(nombre);
    }

    @Then("deberia ver productos que contengan {string}")
    public void validaBusqueda(String texto) {
        Assertions.assertTrue(
                catalogPage.anyVisibleTextContains(texto) || catalogPage.anyVisibleTextContainsIgnoreCase(texto),
                "ERROR: No se encontró ningún producto que contenga: " + texto
        );
    }

    @When("filtra productos por categoria {string}")
    public void filtraCategoria(String categoria) {
        catalogPage.openFilterDropdown();
        catalogPage.selectCategory(categoria);
    }

    @Then("deberia ver productos de la categoria {string}")
    public void validaCategoria(String categoria) {
        Assertions.assertTrue(
                catalogPage.anyVisibleTextContainsIgnoreCase(categoria)
                        || (categoria.equalsIgnoreCase("Electrónica") && catalogPage.anyVisibleTextContainsIgnoreCase("Electronica")),
                "ERROR: No se evidencia que el filtro '" + categoria + "' esté aplicado/visible"
        );
    }
}
