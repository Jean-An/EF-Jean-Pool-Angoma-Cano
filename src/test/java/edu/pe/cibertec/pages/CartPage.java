package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CartPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CartPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    private WebElement waitVisible(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private void scrollToTextContains(String text){
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"
            ));
        } catch (Exception ignored) {}
    }

    private void clickByTextOrClickableParent(String textContains){
        By labelBy = AppiumBy.xpath("//android.widget.TextView[contains(@text,'" + textContains + "')]");
        scrollToTextContains(textContains);

        WebElement label = waitVisible(labelBy);
        WebElement el = label;

        for (int i = 0; i < 6; i++){
            String clickable = el.getAttribute("clickable");
            if ("true".equalsIgnoreCase(clickable)){
                el.click();
                return;
            }
            el = el.findElement(By.xpath(".."));
        }

        // fallback
        label.click();
    }

    public boolean isCartDisplayed(){
        try{
            return waitVisible(AppiumBy.xpath("//android.widget.TextView[@text=\"Carrito de Compras\"]")).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isCartEmpty(){
        try{
            return waitVisible(AppiumBy.xpath("//android.widget.TextView[@text=\"Tu carrito está vacío\"]")).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isProductInCart(String productName){
        try{
            return waitVisible(AppiumBy.xpath("//android.widget.TextView[@text='"+productName+"']")).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public void clickProcederAlPago(){
        try {
            clickByTextOrClickableParent("Proceder");
        } catch (Exception e1){
            try { clickByTextOrClickableParent("Checkout"); }
            catch (Exception e2){
                try { clickByTextOrClickableParent("Finalizar"); }
                catch (Exception e3){ clickByTextOrClickableParent("Pagar"); }
            }
        }
    }

    public void clickBack(){
        waitVisible(AppiumBy.xpath("//android.view.View[@content-desc=\"Volver\"]")).click();
    }

    public void goBack(){
        this.driver.navigate().back();
    }
}
