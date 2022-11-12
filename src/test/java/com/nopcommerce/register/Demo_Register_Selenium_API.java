package com.nopcommerce.register;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Demo_Register_Selenium_API {

    private WebDriver driver;
    private Select select;
    private JavascriptExecutor jsExecutor;
    String firstName, lastName, dayOfBirth, monthOfBirth, yearOfBirth, emailAddress, password, companyName;

    @BeforeClass
    public void beforeClass(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();


     //   driver.get("https://demo.nopcommerce.com/");
        firstName = "Cody";
        lastName = "Nam Vo";
        dayOfBirth = "20";
        monthOfBirth = "9";
        yearOfBirth = "1996";
        emailAddress = "codynamvo.una@cody.test";
        companyName = "Dinh Nam";
        password ="Cody1312";
    }

    @Test
    public void User_Register_01_Empty_Data(){
        driver.get("https://demo.nopcommerce.com/");

        driver.findElement(By.className("ico-register")).click();

        driver.findElement(By.id("register-button")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("span#FirstName-error")).getText(),"First name is required.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#LastName-error")).getText(),"Last name is required.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#Email-error")).getText(),"Email is required.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#Password-error")).getText(),"Password is required.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#ConfirmPassword-error")).getText(),"Password is required.");


    }

    @Test
    public void User_Register_02_Successfully() {
        driver.get("https://demo.nopcommerce.com/");
        driver.findElement(By.className("ico-register")).click();
        driver.findElement(By.cssSelector("input#FirstName")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input#LastName")).sendKeys(lastName);

        By dateLocator = By.xpath("//select[@name='DateOfBirthDay']");
        scrollToElement(dateLocator);
        select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']")));
        sleepInSecond(2);
        Assert.assertEquals(select.getOptions().size(), 32);
        sleepInSecond(2);
        select.selectByVisibleText(dayOfBirth);
        Assert.assertEquals(select.getFirstSelectedOption().getText(),dayOfBirth);
        Assert.assertFalse(select.isMultiple());


        select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']")));
        Assert.assertEquals(select.getOptions().size(),13);
        select.selectByValue(monthOfBirth);
        Assert.assertEquals(select.getFirstSelectedOption().getText(),"September");
        Assert.assertFalse(select.isMultiple());

//        select = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']")));
//        Assert.assertEquals(select.getOptions().size(),112);
//        select.selectByVisibleText(yearOfBirth);
//        Assert.assertEquals(select.getFirstSelectedOption().getText(),yearOfBirth);
//        Assert.assertFalse(select.isMultiple());
//
//        By parentLocator = By.xpath("//select[@name='DateOfBirthYear']");
//        By childLocator = By.xpath("//select[@name='DateOfBirthYear']//option");
//
//        selectItemInDropdown(parentLocator, childLocator, yearOfBirth);




        driver.findElement(By.cssSelector("input[name='Email']")).sendKeys(emailAddress);

        driver.findElement(By.cssSelector("input#Company")).sendKeys(companyName);

        By passwordLocator = By.cssSelector("input#Password");
        scrollToElement(passwordLocator);
        sleepInSecond(1);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys(password);
        sleepInSecond(2);



        driver.findElement(By.cssSelector("button#register-button")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Your registration completed']")).isDisplayed());

    }



    @AfterClass
    public void afterClass(){
        driver.quit();
    }


    public void sleepInSecond(long timeOutInSecond) {
        try {
            Thread.sleep(timeOutInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectItemInDropdown(By parentLocator, By childBy, String expectedItem ) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(parentLocator)).click();
        sleepInSecond(1);
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(childBy));

        List<WebElement> allItems = driver.findElements(childBy) ;

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedItem)){
                item.click();
            } else {
                jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                item.click();
            }
            break;
        }


    }

    public void scrollToElement(By locatorItem) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locatorItem));
    }



    private WebDriverWait explicitWait;
}
