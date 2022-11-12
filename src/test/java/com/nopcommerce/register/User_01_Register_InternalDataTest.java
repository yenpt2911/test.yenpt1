package com.nopcommerce.register;

import commons.BaseTest;
import commons.PageGeneratorManager;
import dataTest.EmployeeData;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.HomePageObject;
import pageObject.RegisterPageObject;

public class User_01_Register_InternalDataTest extends BaseTest {

    WebDriver driver;
    String emailAddress;
    HomePageObject homePage;
    EmployeeData employeeData;
    RegisterPageObject registerPage;


    @Parameters({"browser", "url"})
    @BeforeClass
    public void beforeClass(String browserName, String appUrl){
        log.info("Pre-condition - STEP O1: Open browser'" + browserName + "' and navigate to '" + appUrl + "'");
        driver = getBrowserDriver(browserName, appUrl);
        homePage = PageGeneratorManager.getHomePage(driver);

        employeeData = EmployeeData.getEmployee();
        emailAddress = getRandomEmail();

        log.info("Pre-condtion - STEP 02: Click to 'Register' link");
        homePage.openHeaderPageByClass(driver, "ico-register");
        registerPage = PageGeneratorManager.getRegisterPage(driver);
    }

    @Test
    public void User_Register_01_Empty_Data(){
        log.info("User_Register_01_Empty_Data - STEP 01: Click to 'Register' button");
        registerPage.clickToButtonByText(driver,"Register");

        log.info("User_Register_01_Empty_Data - STEP 02: Verify error message displayed at 'First name' text box");
        registerPage.isErrorMessageDisplayed(driver,"FirstName-error");

        log.info("User_Register_01_Empty_Data - STEP 03: Verify error message displayed at 'Last name' text box");
        registerPage.isErrorMessageDisplayed(driver, "LastName-error");

        log.info("User_Register_01_Empty_Data - STEP 04: Verify error message displayed at 'Email' text box");
        registerPage.isErrorMessageDisplayed(driver,"Email-error");

        log.info("User_Register_01_Empty_Data - STEP 05: Verify error message displayed at 'Password' text box");
        registerPage.isErrorMessageDisplayed(driver,"Password-error");

        log.info("User_Register_01_Empty_Data - STEP 06: Verify error message displayed at 'Confirm password' text box");
        registerPage.isErrorMessageDisplayed(driver,"ConfirmPassword-error");

    }

    @Test
    public void User_Register_02_Invalid_Email(){
        log.info("User_Register_02_Invalid_Email - STEP 01: Enter to 'FirstName' text box with value:" + employeeData.getFirstName());
        registerPage.enterToTextboxByID(driver, "FirstName", employeeData.getFirstName());

        log.info("User_Register_02_Invalid_Email - STEP 02: Enter to 'LastName' textbox with value:" + employeeData.getLastName());
        registerPage.enterToTextboxByID(driver, "LastName", employeeData.getLastName());

        log.info("User_Register_02_Invalid_Email - STEP 03: Enter to 'Email' text box with value:" + employeeData.getEmailInvalid());
        registerPage.enterToTextboxByID(driver,"Email", employeeData.getEmailInvalid() );

        log.info("User_Register_02_Invalid_Email - STEP 04: Enter to 'Password' text box with value: " + employeeData.getPassword());
        registerPage.enterToTextboxByID(driver, "Password", employeeData.getPassword());

        log.info("User_Register_02_Invalid_Email - STEP 04: Enter to 'Confirm Password' textbox with value: " + employeeData.getPassword());
        registerPage.clickToButtonByText(driver, "Register");

        log.info("User_Register_02_Invalid_Email - STEP 05: Verify error message displayed at 'Email' text box");
        registerPage.isErrorMessageDisplayed()
    }




    @AfterClass





}
