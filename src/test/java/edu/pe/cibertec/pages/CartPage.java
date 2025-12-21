package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public CartPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public boolean isCartDisplayed(){
        try{
            WebElement titulo = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.xpath("//android.widget.TextView[@text=\"Carrito de Compras\"]")
                    ));
            return  titulo.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isCartEmpty(){
        try{
            WebElement titulo = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.xpath("//android.widget.TextView[@text=\"Tu carrito está vacío\"]")
                    ));
            return  titulo.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }
    public boolean isProductInCart(String productName){
        try{
            WebElement product = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.xpath("//android.widget.TextView[@text='"+productName+"']")
                    ));
            return  product.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public  void clickBack(){
        WebElement backButton =  wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.xpath("//android.view.View[@content-desc=\"Volver\"]")
                ));
    }

    public void goBack(){
        this.driver.navigate().back();
    }
}
