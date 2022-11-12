package commons;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BasePage {

    public static  BasePage getBasePage(){
        return new BasePage();
    }

    public void openPageUrl(WebDriver driver, String pageUrl){
        driver.get(pageUrl);

    }

    public String getPageTitle(WebDriver driver){
        return driver.getTitle();
    }

    public String getCurrentPageUrl (WebDriver driver){
        return driver.getCurrentUrl();
    }
    public String getPageSourceCode (WebDriver driver){
        return driver.getPageSource();
    }

    public Set<Cookie> getAllCookies (WebDriver driver){
        return driver.manage().getCookies();
    }

    public void setAllCookies(WebDriver driver, Set<Cookie> allCookies) {
        for (Cookie cookie : allCookies) {
            driver.manage().addCookie(cookie);
        }
    }

    public void backToPage(WebDriver driver){
        driver.getPageSource();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void refreshToPage(WebDriver driver){
        driver.navigate().refresh();
    }

    public Alert waitForAlertPresence(WebDriver driver){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver){
        waitForAlertPresence(driver).accept();
    }

    public void cancelAlert(WebDriver driver){
        waitForAlertPresence(driver).dismiss();
    }

    public String getAlertText(WebDriver driver){
        return waitForAlertPresence(driver).getText();
    }

    public void sendKeyToAlert(WebDriver driver, String textValue){
        waitForAlertPresence(driver).sendKeys(textValue);
    }

    public void switchToWindowByID(WebDriver driver, String parentID){
        Set<String> allWindows = driver.getWindowHandles();

        for (String id: allWindows){
            if(!id.equals(parentID)){
                driver.switchTo().window(id);
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String expectedTitle){
        Set<String> allWindows = driver.getWindowHandles();

        for (String id : allWindows){
            driver.switchTo().window(id);
            String windowTitle = driver.getTitle();
            if (windowTitle.equals(expectedTitle)){
                break;
            }
        }
    }

    public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();

        for (String id: allWindows){
            if (!id.equals(parentID)){
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    public By getByXpath(String xpathLocator) {
        return By.xpath(xpathLocator);
    }

    public String getDynamicLocator(String xpathLocator, String... params) {
        return String.format(xpathLocator, (Object[]) params);
    }

    public WebElement getWebElement(WebDriver driver, String xpathLocator){
        return driver.findElement(getByXpath(xpathLocator));
    }

    public WebElement getWebElement(WebDriver driver, String xpathLocator, String... params){
        return driver.findElement(getByXpath(getDynamicLocator(xpathLocator, params)));
    }

    public List<WebElement> getListWebElement (WebDriver driver, String xpathLocator){
        return driver.findElements(getByXpath(xpathLocator));
    }

    public List<WebElement> getListWebElement (WebDriver driver, String xpathLocator, String... params) {
        return driver.findElements(getByXpath(getDynamicLocator(xpathLocator, params)));
    }

    public void clickToElement(WebDriver driver, String xpathLocator){
        getWebElement(driver, xpathLocator).click();
    }

    public void clickToElement(WebDriver driver, String xpathLocator, String... params){
        getWebElement(driver, xpathLocator, params).click();
    }

    public void sendKeyToElement(WebDriver driver, String xpathLocator, String textValue) {
        WebElement element = getWebElement(driver, xpathLocator);
        element.clear();
        element.sendKeys(textValue);
    }

    public void sendKeyToElement(WebDriver driver, String xpathLocator, String textValue, String... params) {
        WebElement element = getWebElement(driver, xpathLocator, params);
        element.clear();
        element.sendKeys(textValue);
    }

    public void selectItemDefaultDropdown(WebDriver driver, String xpathLocator, String textItem) {
        select = new Select(getWebElement(driver, xpathLocator));
        select.selectByValue(textItem);
    }

    public void selectItemDefaultDropdown(WebDriver driver, String xpathLocator, String textItem, String... params) {
        select = new Select(getWebElement(driver, xpathLocator, params));
        select.selectByValue(textItem);

    }
    public String getSelectedItemDefaultDropdown(WebDriver driver, String xpathLocator, String textItem) {
        select = new Select(getWebElement(driver, xpathLocator));
       return select.getFirstSelectedOption().getText();

    }

    public String getSelectedItemDefaultDropdown(WebDriver driver, String xpathLocator, String... params) {
        select = new Select(getWebElement(driver, xpathLocator, params));
        return select.getFirstSelectedOption().getText();
    }

    public void selectDefaultDropdownByText (WebDriver driver, String xpathLocator, String itemText) {
        select = new Select(getWebElement(driver,xpathLocator));
        select.selectByVisibleText(itemText);
    }

    public void selectDefaultDropdownByText (WebDriver driver, String xpathLocator, String itemText, String... params) {
        select = new Select(getWebElement(driver, xpathLocator, params));
        select.selectByVisibleText(itemText);
    }

    public boolean isDropdownMultiple(WebDriver driver, String xpathLocator) {
        select = new Select(getWebElement(driver, xpathLocator));
        return select.isMultiple();
    }

    public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedTextItem) {
        getWebElement(driver, parentXpath).click();
        sleepInSecond(1);

        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);

        List <WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));
        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedTextItem)) {
                if (item.isDisplayed()) {
                    item.click();
                } else {
                    jsExecutor = (JavascriptExecutor) driver;
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                    item.click();
                }
                break;
            }
        }
    }

    public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedTextItem, String... params) {
        getWebElement(driver, parentXpath, params);
        sleepInSecond(1);

        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);

        List <WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(getDynamicLocator(childXpath,params))));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedTextItem)){
                if(item.isDisplayed()) {
                    item.click();
                } else {
                    jsExecutor = (JavascriptExecutor) driver;
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                    item.click();
                }
                break;
            }
        }
    }

    public void selectItemEditableInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedTextItem) {
       WebElement element = getWebElement(driver, parentXpath);
       element.click();
       element.sendKeys(expectedTextItem);

       List <WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));
       for (WebElement item : allItems) {
           if(item.getText().trim().equals(expectedTextItem)){
               if (item.isDisplayed()){
                   item.click();
               } else {
                   jsExecutor = (JavascriptExecutor) driver;
                   jsExecutor.executeScript("arguments[0].scrollIntoView(true);",item);
                   item.click();
               }
               break;
           }
       }
    }

    public void selectItemEditableInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedTextItem, String... params){
        WebElement element = getWebElement(driver, parentXpath, params);
        element.click();
        element.sendKeys(expectedTextItem);

        List <WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(getDynamicLocator(childXpath, params))));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedTextItem)){
                if (item.isDisplayed()){
                    item.click();
                } else {
                    jsExecutor = (JavascriptExecutor) driver;
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                    item.click();
                }
                break;
            }
        }
    }

    public String getHiddenText(WebDriver driver, String cssLocator){
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return document.querySelector(\"" + cssLocator + "\").value");
    }

    public void sleepInSecond(long timeOutInSecond) {
        try {
            Thread.sleep(timeOutInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName){
        return getWebElement(driver, xpathLocator).getAttribute(attributeName);
    }

    public String getElementText(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).getText();
    }

    public String getElementText(WebDriver driver, String xpathLocator, String... params) {
        return getWebElement(driver, xpathLocator, params).getText();
    }

    public String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName) {
        return getWebElement(driver, xpathLocator).getCssValue(propertyName);
    }

    public String getHexaColorFromRgba(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    public int getElementSize(WebDriver driver, String xpathLocator) {
        return getListWebElement(driver, xpathLocator).size();
    }

    public int getElementSize(WebDriver driver, String xpathLocator, String... params) {
        return getListWebElement(driver, xpathLocator, params).size();
    }

    public void checkToDefaultCheckboxRadio(WebDriver driver, String xpathLocator) {
        WebElement element = getWebElement(driver, xpathLocator);
        if(!element.isSelected()) {
            element.click();
        }
    }

    public void checkToDefaultCheckBox(WebDriver driver, String xpathLocator, String... params) {
        WebElement element = getWebElement(driver, xpathLocator, params);
        if (!element.isSelected()){
            element.click();
        }
    }

    public void uncheckToDefaultCheckbox(WebDriver driver, String xpathLocator) {
        WebElement element = getWebElement(driver, xpathLocator);
        if (element.isSelected()){
            element.click();
        }
    }

    public void uncheckToDefaultCheckbox(WebDriver driver, String xpathLocator, String... params) {
        WebElement element = getWebElement(driver, xpathLocator, params);
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isElementDisplayed(WebDriver driver, String xpathLocator) {
        try {
            return getWebElement(driver, xpathLocator).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isElementDisplayed(WebDriver driver, String xpathLocator, String... params) {
        try {
            return getWebElement(driver, xpathLocator, params).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isElementUndisplayed(WebDriver driver, String xpathLocator) {
        overrideGlobalTimeout(driver, shortTimeout);
        List<WebElement> elements = getListWebElement(driver, xpathLocator);
        overrideGlobalTimeout(driver, longTimeout);

        if(elements.size() ==0){
            return true;
        } else if(elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }

    }


    private void overrideGlobalTimeout(WebDriver driver, long timeout) {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    public boolean isElementEnable(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).isEnabled();
    }

    public boolean isElementSelected(WebDriver driver, String xpathLocator) {
        return getWebElement(driver, xpathLocator).isSelected();
    }

    public void switchToFrameIframe(WebDriver driver, String xpathLocator) {
        driver.switchTo().frame(getWebElement(driver, xpathLocator));
    }

    public void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public void doubleClickToElement(WebDriver driver, String xpathLocator) {
      action = new Actions(driver);
      action.doubleClick(getWebElement(driver, xpathLocator)).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String xpathLocator) {
        action = new Actions(driver);
        action.moveToElement(getWebElement(driver, xpathLocator)).perform();
    }

    public void rightClickToElement(WebDriver driver, String xpathLocator) {
        action = new Actions(driver);
        action.contextClick(getWebElement(driver, xpathLocator)).perform();
    }

    public void dragAndDrop(WebDriver driver, String sourceLocator, String targetLocator) {
        action = new Actions(driver);
        action.dragAndDrop(getWebElement(driver,sourceLocator), getWebElement(driver, targetLocator)).perform();
    }

    public void pressKeyToElement(WebDriver driver, String xpathLocator, Keys key) {
        action = new Actions(driver);
        action.sendKeys(getWebElement(driver, xpathLocator), key).perform();
    }

    public void scrollToBottomPage(WebDriver driver) {
        jsExecutor =(JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void highlightElement(WebDriver driver, String xpathLocator) {
        jsExecutor =(JavascriptExecutor) driver;
        WebElement element = getWebElement(driver, xpathLocator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void scrollToElement(WebDriver driver, String xpathLocator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);",getWebElement(driver,xpathLocator));
    }

    public void clickToElementByJS(WebDriver driver, String xpathLocator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver,xpathLocator));
    }

    public void removeAttributeOfElement(WebDriver driver, String xpathLocator, String attributeRemove) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove+ "');", getWebElement(driver, xpathLocator));
    }

    public String getElementValidationMessage(WebDriver driver, String xpathLocator) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(driver, xpathLocator));
    }

    public void waitForElementVisible(WebDriver driver, String xpathLocator){
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
    }

    public void waitForElementVisible(WebDriver driver, String xpathLocator, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(xpathLocator, params))));
    }

    public void waitForAllElementVisible(WebDriver driver, String xpathLocator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator)));
    }

    public void waitForElementInvisible(WebDriver driver, String xpathLocator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator)));
    }

    public void waitForElementInvisible(WebDriver driver, String xpathLocator, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(xpathLocator, params))));
    }

    public void waitForAllElementInvisible(WebDriver driver, String xpathLocator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, xpathLocator)));
    }

    public void waitForAllElementInvisible(WebDriver driver, String xpathLocator, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, xpathLocator, params)));
    }

    public void waitForElementClickable(WebDriver driver, String xpathLocator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator)));
    }

    public void waitForElementClickable(WebDriver driver, String xpathLocator, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(xpathLocator, params))));
    }

    public boolean isDataStringSortedAscending(WebDriver driver, String locator){
        // Khai báo 1 Array List
        ArrayList<String> arrayList = new ArrayList<>();

        // Tìm tất cả element matching với điều kiện
        List<WebElement> elementList = getListWebElement(driver, locator);

        // Lấy text của từng element add vào Array List
        for (WebElement element:elementList){
            arrayList.add(element.getText());
        }

        // Coppy qua 1 array list mới để sort trong code
        ArrayList<String>sortedList = new ArrayList<>();

        for (String child:arrayList){
            sortedList.add(child);
        }
        // Thực hiện sort ASC
        Collections.sort(sortedList);

        // Verify 2 array bằng nhau
        return sortedList.equals(arrayList);

    }

    public boolean isDataStringSortedDescending(WebDriver driver, String locator) {
        // Khai báo 1 array List
        ArrayList<String> arrayList = new ArrayList<>();

        // Tìm tất cả element matching với điều kiện
        List<WebElement> elementList = getListWebElement(driver, locator);

        // Lấy text của từng element add vào ArrayList
        for (WebElement element:elementList) {
            arrayList.add(element.getText());
        }

        // Coppy qua 1 array list mới để sort
        ArrayList<String> sortedList = new ArrayList<>();

        for (String child:arrayList){
            sortedList.add(child);
        }

        // Thực hiện sort ASC
        Collections.sort(sortedList);

        //Reverse data để sort DESC
        Collections.reverse(sortedList);

        //Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì kết quả trả về sai
        return sortedList.equals(arrayList);

    }

    public boolean isDataFloatSortedAscending(WebDriver driver, String locator){
        // Khai báo 1 ArrayList
        ArrayList<Float> arrayList = new ArrayList<Float>();

        // Tìm tất cả element matching với điều kiện
        List<WebElement> elementList = getListWebElement(driver, locator);

        // Lấy text của từng element add vào ArrayList
        for (WebElement element:elementList){
            arrayList.add(Float.parseFloat(element.getText().replace("$","").trim()));
        }

        // Coppy qua 1 array list để sort trong code
        ArrayList<Float> sortedList = new ArrayList<Float>();

        for (Float child:arrayList){
            sortedList.add(child);
        }

        // Thực hiện sort ASC
        Collections.sort(sortedList);

        // Verify 2 array bằng nhau - nếu dữ liệu sort trên UI không chính xác thì trả về kết quả sai
        return sortedList.equals(arrayList);

    }

    public boolean isDataDateSortedAscending(WebDriver driver, String locator) {
        ArrayList<Date> arrayList = new ArrayList<Date>();

        List<WebElement> elementList = getListWebElement(driver, locator);

        for (WebElement element:elementList){
            arrayList.add(convertStringToDate(element.getText()));
        }

        ArrayList<Date> sortedList = new ArrayList<Date>();
        for (Date child: arrayList){
            sortedList.add(child);
        }
        Collections.sort(sortedList);
        return sortedList.equals(arrayList);
    }

    public Date convertStringToDate(String dateInString) {
        dateInString = dateInString.replace(",","");
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }




    public void openHeaderPageByClass(WebDriver driver,  String headerClass) {
        waitForElementClickable(driver, BasePageUI.HEADER_PAGE_BY_CLASS, headerClass);
        clickToElement(driver, BasePageUI.HEADER_PAGE_BY_CLASS, headerClass);

    }

    public void clickToButtonByText(WebDriver driver, String buttonName) {
        waitForElementClickable(driver, BasePageUI.BUTTON_BY_TEXT, buttonName);
        clickToElement(driver, BasePageUI.BUTTON_BY_TEXT, buttonName);
    }


    private Select select;
    private Actions action;
    private long longTimeout;
    private long shortTimeout;
    private JavascriptExecutor jsExecutor;
    private WebDriverWait explicitWait;


    public boolean isErrorMessageDisplayed(WebDriver driver, String idValue) {
        waitForElementVisible(driver, BasePageUI.ERROR_MESSAGE_BY_ID, idValue);
        return isElementDisplayed(driver, BasePageUI.ERROR_MESSAGE_BY_ID, idValue);
    }

    public void enterToTextboxByID(WebDriver driver, String idValue, String value) {
        waitForElementVisible(driver, BasePageUI.TEXTBOX_BY_ID, idValue);
        sendKeyToElement(driver, BasePageUI.TEXTBOX_BY_ID, value, idValue);
    }
}
