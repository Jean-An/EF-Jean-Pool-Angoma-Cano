package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumElementLocatorFactory;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public HomePage(AndroidDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public boolean isHomePageDisplayed(){
        try{
            WebElement titulo = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.xpath("//android.widget.TextView[@text='Productos']")
                    ));
            return  titulo.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public boolean isProductDisplayed(String productName){
        try{
            WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.TextView[@text='"+productName+"']")
            ));
            return  product.isDisplayed();
        }catch (Exception e){
            return  false;
        }
    }

    public void clickProduct(String productName){

        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text='"+productName+"']")
        ));
        product.click();

    }

    public void clickCartTab(){
        WebElement carTab = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text=\"Carrito\"]")
        ));
        carTab.click();
    }

    public void clickProfileTab() {
        WebElement profileTab = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text=\"Perfil\"]")
        ));
        profileTab.click();
    }

    public void clickFilter(String filterName){
        WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text='"+filterName+"']")
        ));
        filter.click();
    }

    public void searchProduct(String productoName){
        WebElement searchField=wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.className("android.widget.EditText")
        ));
        searchField.click();
        searchField.sendKeys(productoName);
    }

}
