package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CatalogPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CatalogPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isCatalogDisplayed() {
        try {
            WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.TextView[@text='Productos']")
            ));
            return titulo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void search(String productName) {
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.className("android.widget.EditText")
        ));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(productName);
    }

    public void openFilterDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text='Todos']")
        )).click();
    }

    public void selectCategory(String categoryName) {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text='"+categoryName+"']")
        )).click();
    }

    public List<WebElement> getVisibleProductNameElements() {
        // Ajusta este locator con Appium Inspector si tu app tiene IDs.
        return driver.findElements(AppiumBy.xpath("//android.widget.TextView"));
    }

    public boolean anyVisibleTextContains(String text) {
        return getVisibleProductNameElements().stream()
                .map(WebElement::getText)
                .filter(t -> t != null && !t.isBlank())
                .anyMatch(t -> t.contains(text));
    }
}
