package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public ProductDetailPage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public boolean isProductDetailDisplayed(){
        try{
            WebElement titulo = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.xpath("//android.widget.TextView[@text=\"Detalle del Producto\"]")
                    ));
            return  titulo.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isProductAddedMessageDisplayed(){
        try{
            WebElement message = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.xpath("//android.widget.TextView[@text=\"Producto agregado al carrito\"]")
                    ));
            return  message.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public void ClickAddToCart(){
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text=\"Agregar al carrito\"]")
        ));
        addButton.click();
    }

    public void incrementQuantity(){
        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.view.View[@content-desc=\"Aumentar\"]")
        ));
        plusButton.click();
    }

    public void decrementarQuantity(){
        WebElement minusButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.view.View[@content-desc=\"Disminuir\"]")
        ));
        minusButton.click();
    }

    public void goBack(){
        this.driver.navigate().back();
    }

}
