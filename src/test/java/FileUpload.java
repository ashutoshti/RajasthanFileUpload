import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class FileUpload {
    static WebDriver driver;
    Properties properties = new Properties();
    private WebDriverWait webDriverWait;

    @BeforeTest
    private void openBrowser() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/Browser Driver/chromedriver");
        driver = new ChromeDriver();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/Properties/ConfigurationDetails"));
            properties.load(fileInputStream);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("ConfigurationDetails File Not Found " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("Input Output Exception " + ioException);
        }
    }

    @Test()
    private void openWebsite() {
        /*Open Website URL  */
        driver.get(properties.getProperty("URL"));
        /*Enter Website UserName*/
        driver.findElement(By.id("id")).sendKeys(properties.getProperty("UserName"));
        /*Enter Website Password*/
        driver.findElement(By.id("pwd")).sendKeys(properties.getProperty("Password"));
        /*Click on Login Button*/
        driver.findElement(By.xpath("//div[5]//input[1]")).click();
        /*Wait Till Scheduling Drop Down Visible*/
        webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Scheduling')]")));
    }

    @Test(dependsOnMethods = {"openWebsite"})
    private void fileUploadPage() {
        Actions actions = new Actions(driver);
        /*Navigate To File Upload Section*/
        WebElement scheduling = driver.findElement(By.xpath("//a[contains(text(),'Scheduling')]"));
        actions.moveToElement(scheduling).perform();
        WebElement rePSS = driver.findElement(By.xpath("//a[contains(text(),'RE PSS')]"));
        actions.moveToElement(rePSS).perform();
        driver.findElement(By.xpath("//a[contains(text(),'RE Schedule Entry')]")).click();
        actions.moveByOffset(200, 230).perform();
        /*Change The Category Drop Down Solar To Solar_Wind */
        driver.switchTo().frame("iframe_content");
        webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'Category')]")));
        driver.findElement(By.xpath("//select[@id='selectedCategory']")).click();
        Select categoryDropDown = new Select(driver.findElement(By.name("selectedCategory")));
        categoryDropDown.selectByValue("Solar_Wind");
    }

    @Test(dependsOnMethods = {"fileUploadPage"})
    private void fileUploadFunctionality() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement plantDropDown = driver.findElement(By.xpath("//select[@id='selectedGenco']"));
        List<WebElement> plantList = plantDropDown.findElements(By.tagName("option"));
        for (WebElement element : plantList) {
            String value = element.getText();
            System.out.println("Values in Drop Down is: " + value);

            if (value.contains("Select Plant")) {
                System.out.println("Value is 0 " + value);
                selectReasonofUpload();
            } else if (value.contains("BAORI")) {
                selectPssName(value);
                System.out.println("Value is 1 " + value);
                selectReasonofUpload();
            } else if (value.contains("CHAMU")) {
                selectPssName(value);
                System.out.println("Value is 2 " + value);
                selectReasonofUpload();
            } else if (value.contains("KHOOD")) {
                System.out.println("Value is 3 " + value);
                selectReasonofUpload();
            } else if (value.contains("OSIAN")) {
                System.out.println("Value is 4 " + value);
                selectReasonofUpload();
            } else if (value.contains("PS8")) {
                System.out.println("Value is 5 " + value);
                selectReasonofUpload();
            } else if (value.contains("KALADUNGAR")) {
                System.out.println("Value is 6 " + value);
                selectReasonofUpload();
            } else if (value.contains("LUDARWA")) {
                System.out.println("Value is 7 " + value);
                selectReasonofUpload();
            } else if (value.contains("MOKALA")) {
                System.out.println("Value is 8 " + value);
                selectReasonofUpload();
            } else if (value.contains("DEBARI")) {
                System.out.println("Value is 9 " + value);
                selectReasonofUpload();
            } else if (value.contains("RAMGARH_NIDHI")) {
                System.out.println("Value is 10 " + value);
                selectReasonofUpload();
            } else if (value.contains("TEJUWA_II")) {
                System.out.println("Value is 11 " + value);
                selectReasonofUpload();
            } else if (value.contains("BHESADA")) {
                System.out.println("Value is 12 " + value);
                selectReasonofUpload();
            } else if (value.contains("DANGRI")) {
                System.out.println("Value is 13 " + value);
                selectReasonofUpload();
            } else if (value.contains("RAJGARH")) {
                System.out.println("Value is 14 " + value);
                selectReasonofUpload();
            }
        }
    }


    private void selectPssName(String pssName) {
        Select pssDropDownName = new Select(driver.findElement(By.xpath("//select[@id='selectedGenco']")));
        pssDropDownName.selectByValue(pssName);
    }

    private void selectReasonofUpload() {
        Select reasonDropDown = new Select(driver.findElement(By.xpath("//select[@id='selectedReason']")));
        reasonDropDown.selectByValue("REVISION");
        driver.findElement(By.xpath("//button[@id='uploadBtn']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        utilities.screenshot();
        driver.findElement(By.xpath("//i[@class='fa fa-times']")).click();

    }

    @AfterTest(enabled = true)
    private void closeBrowser() {
        driver.quit();
    }
}