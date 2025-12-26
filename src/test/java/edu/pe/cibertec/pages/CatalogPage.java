package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CatalogPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By productosTitleBy = AppiumBy.xpath("//android.widget.TextView[@text='Productos']");
    private final By searchFieldBy = AppiumBy.className("android.widget.EditText");

    private final By filterLabelBy = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.TextView\").textMatches(\"(?i)(todos|all|electr[oó]nica|electronica)\")"
    );

    public CatalogPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private boolean exists(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCatalogDisplayed() {
        try {
            WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(productosTitleBy));
            return titulo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void search(String productName) {
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(searchFieldBy));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(productName);
        try { driver.hideKeyboard(); } catch (Exception ignored) {}
    }

    public void openFilterDropdown() {
        if (exists(filterLabelBy)) {
            wait.until(ExpectedConditions.elementToBeClickable(filterLabelBy)).click();
            return;
        }

        By todosBy = AppiumBy.xpath("//android.widget.TextView[@text='Todos']");
        if (exists(todosBy)) {
            wait.until(ExpectedConditions.elementToBeClickable(todosBy)).click();
            return;
        }

        By allBy = AppiumBy.xpath("//android.widget.TextView[@text='All']");
        wait.until(ExpectedConditions.elementToBeClickable(allBy)).click();
    }

    private By categoryLocator(String categoryName) {
        if (categoryName.equalsIgnoreCase("Electrónica") || categoryName.equalsIgnoreCase("Electronica")) {
            return AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.TextView\").textMatches(\"(?i)electr[oó]nica\")"
            );
        }
        return AppiumBy.xpath("//android.widget.TextView[@text='" + categoryName + "']");
    }

    public void selectCategory(String categoryName) {
        By by = categoryLocator(categoryName);
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    public List<WebElement> getAllTextViews() {
        return driver.findElements(AppiumBy.className("android.widget.TextView"));
    }

    public boolean anyVisibleTextContains(String text) {
        String target = text == null ? "" : text.trim();
        if (target.isEmpty()) return false;

        List<WebElement> views = getAllTextViews();
        for (WebElement v : views) {
            try {
                if (!v.isDisplayed()) continue;
                String t = v.getText();
                if (t != null && !t.isBlank() && t.contains(target)) return true;
            } catch (Exception ignored) {}
        }
        return false;
    }

    public boolean anyVisibleTextContainsIgnoreCase(String text) {
        String target = text == null ? "" : text.trim().toLowerCase();
        if (target.isEmpty()) return false;

        List<WebElement> views = getAllTextViews();
        for (WebElement v : views) {
            try {
                if (!v.isDisplayed()) continue;
                String t = v.getText();
                if (t != null && !t.isBlank() && t.toLowerCase().contains(target)) return true;
            } catch (Exception ignored) {}
        }
        return false;
    }
}
