package com.nopcommerce.register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.HomePageObject;
import pageObject.RegisterPageObject;

import java.util.Scanner;

public class User_01_Register extends BaseTest {
    private WebDriver driver;
    String emailAddress;
    HomePageObject homePage;
    RegisterPageObject registerPage;

    @Parameters({"browser", "url"})
    @BeforeClass
    public void beforeClass(String browserName, String appUrl) {
        log.info("Pre-condition - STEP 01: Open browser' "+ browserName + "' and navigate to '" + appUrl+ "'");
        driver = getBrowserDriver(browserName, appUrl);
        homePage = PageGeneratorManager.getHomePage(driver);

        //Init TestDATA
        // Gán value cho emailAddress

        log.info("Pre-condition - STEP 02: Click to 'Register' link");
        homePage.openHeaderPageByClass(driver,"ico-register");
        registerPage = PageGeneratorManager.getRegisterPage(driver);

    }
    @Test
    public void User_Register_01_Empty_Data() {
        log.info("User_Register_01 - STEP 01: Click to 'Register' button");
        registerPage.clickToButtonByText(driver,"Register");

        log.info("User_Register_01 - STEP 02: Verify error message at 'First name' textbox");
        verifyTrue(registerPage.isErrorMessageDisplayed(driver,"FirstName-error"));

//        log.info("User_Register_01 - STEP 01: ");
//
//        log.info("User_Register_01 - STEP 01: ");
//
//        log.info("User_Register_01 - STEP 01: ");
//
//        log.info("User_Register_01 - STEP 01: ");
    }

    @Parameters({"browser"})
    @AfterClass(alwaysRun = true)
    public void afterClass(String browserName) {
        cleanDriverInstance();
    }

    public static void main(String[] args) {

        int height;
        int width;
        int i;
        Scanner sc = new Scanner(System.in);

        System.out.print("height = ");
        height = sc.nextInt();
        System.out.print("width = ");
        width = sc.nextInt();

        for(i = 1; i <= height; i++){
            for(int j = 1; j <= width; j++) {
                System.out.print("*  ");
            }
            System.out.println("");
        }

        sc.close();
    }


}

