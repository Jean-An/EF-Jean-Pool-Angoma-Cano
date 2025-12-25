package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.CatalogPage;
import edu.pe.cibertec.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class CatalogSteps {

    private AndroidDriver driver;
    private LoginPage loginPage;
    private CatalogPage catalogPage;

    @Before("@catalogo")
    public void setUp() {
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        catalogPage = new CatalogPage(driver);
    }

    @After("@catalogo")
    public void tearDown() {
        AppiumConfig.quitDriver();
    }

    @Given("que el usuario esta logueado en la aplicacion")
    public void usuarioLogueado() {
        loginPage.login("admin@test.com", "123456");
        Assertions.assertTrue(catalogPage.isCatalogDisplayed(), "ERROR: No se mostró el catálogo (Productos) tras login");
    }

    @When("navega al catalogo de productos")
    public void navegaCatalogo() {
        Assertions.assertTrue(catalogPage.isCatalogDisplayed(), "ERROR: No estamos en el catálogo");
    }

    @Then("deberia ver la lista de productos disponibles")
    public void verLista() {
        Assertions.assertTrue(catalogPage.anyVisibleTextContains("Laptop")
                        || catalogPage.anyVisibleTextContains("Mouse")
                        || catalogPage.anyVisibleTextContains("Producto"),
                "ERROR: No se detectó ningún producto visible en pantalla");
    }

    @Given("que el usuario esta en el catalogo")
    public void usuarioEnCatalogo() {
        Assertions.assertTrue(catalogPage.isCatalogDisplayed(), "ERROR: No estamos en el catálogo");
    }

    @When("busca el producto {string}")
    public void buscaProducto(String nombre) {
        catalogPage.search(nombre);
    }

    @Then("deberia ver productos que contengan {string}")
    public void validaBusqueda(String texto) {
        Assertions.assertTrue(catalogPage.anyVisibleTextContains(texto),
                "ERROR: No se encontró ningún producto que contenga: " + texto);
    }

    @When("filtra productos por categoria {string}")
    public void filtraCategoria(String categoria) {
        catalogPage.openFilterDropdown();
        catalogPage.selectCategory(categoria);
    }

    @Then("deberia ver productos de la categoria {string}")
    public void validaCategoria(String categoria) {
        // Lo más robusto aquí suele ser validar que el filtro seleccionado se ve en UI (chip/label)
        Assertions.assertTrue(catalogPage.anyVisibleTextContains(categoria),
                "ERROR: No se evidencia que el filtro '" + categoria + "' esté aplicado/visible");
    }
}
