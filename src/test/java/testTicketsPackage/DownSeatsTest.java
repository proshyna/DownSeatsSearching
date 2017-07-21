package testTicketsPackage;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
/**
 * Created by 1 on 14.06.2017.
 */
public class DownSeatsTest {
    private WebDriver driver;
    private WebDriverWait myDynamicElement;

    @Test
    public void main() {
        System.setProperty("webdriver.chrome.driver", "D:\\SeleniumTest\\chromedriver_win32\\chromedriver.exe");

        initDriver();
        myDynamicElement = new WebDriverWait(driver, 30);
        filters();
        searching();
        afterTest();
    }

    private void searching() {

        List<String> expectedCarriageTypes = new ArrayList<String>();

        expectedCarriageTypes.add("Плацкарт");
        expectedCarriageTypes.add("Купе");

        List<String> expectedTrainsNum = new ArrayList<String>();
        expectedTrainsNum.add("358 Л");
        expectedTrainsNum.add("112 Л");
        expectedTrainsNum.add("144 Л");
        //Для поиска любых мест

        for (String typeUnit : expectedCarriageTypes) {
            for (String trainUnit : expectedTrainsNum) {
                try {
                    WebElement foundedButton = driver.findElement(
                            By.xpath("//td[@class='num']/a[contains (text(),'"
                                    + trainUnit + "')]/../../td[@class='place']/div[@title='"
                                    + typeUnit + "'][b>=2]/button"));
                    if (foundedButton!=null)
                    {
                        sendNotificationOnEmail();
                    }

                } catch (NoSuchElementException e) {
                }
            }
        }
    }

        //Для поиска нижних мест
      /*
        List<WebElement> buttons = new ArrayList<WebElement>();
        for (String typeUnit : expectedCarriageTypes) {
            for (String trainUnit : expectedTrainsNum) {
                try {
                    WebElement foundedButton = driver.findElement(
                            By.xpath("//td[@class='num']/a[contains (text(),'"
                                    + trainUnit + "')]/../../td[@class='place']/div[@title='"
                                    + typeUnit + "'][b>=2]/button"));
                    buttons.add(foundedButton);
                } catch (NoSuchElementException e) {
                }
            }
        }





     for (WebElement buttonUnit : buttons) {
            buttonUnit.click();
            try {
                myDynamicElement.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//div[@class='floor floor-1']/div")));
            } catch (Exception e) {
                System.out.println("Somthing wrong 2:( " + e.toString());
            }
            for (int i = 1; i <= getCoaches().size(); i++) {

                try {
                    myDynamicElement.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[@class='coaches']/a[" + i + "]")));
                } catch (Exception e) {
                    System.out.println("Somthing wrong 3:( " + e.toString());
                }
                try {
                    myDynamicElement.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//span[@class='coaches']/a[" + i +
                                    "]/../../../../div[@class='vToolsPopupContent']//div[@class='floor floor-1']/div")));
                } catch (Exception e) {
                    System.out.println("Somthing wrong 4:( " + e.toString());
                }
                if (!driver.findElements(By.xpath(
                        "//div[@class='floor floor-1']/div[@class='down place fr']")).isEmpty()) {
                    System.out.println("Есть нижние места в вагоне " + i + "");
                    sendNotificationOnEmail();
                } else {
                    System.out.println("Нижних мест НЕТУ в вагоне " + i + "");
                }

            }
            driver.findElement(By.xpath("//div[@class='vToolsPopupHeader']/a[@href='javascript://']")).click();
            try {
                myDynamicElement.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                        "//div[@class='vToolsPopup coachScheme']")));
            } catch (Exception e) {
                System.out.println("Somthing wrong 5:( " + e.toString());
            }
            try {
                myDynamicElement.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath
                        ("//tbody/tr[1]//td[@class='place']/div[1]//button[contains(text(), 'Выбрать')]")));
            } catch (Exception e) {
                System.out.println("Somthing wrong 6:( " + e.toString());
            }
        }

    }

    private List<WebElement> getCoaches() {
        return driver.findElements(By.xpath("//span[@class='coaches']/a"));
    }*/


    private void filters() {  // Searching for trains with defined filters

        WebDriverWait myDynamicElement = new WebDriverWait(driver, 30);
        driver.navigate().to("http://booking.uz.gov.ua/");
        try {
            myDynamicElement.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[@id=\"langs\"]//li[1]//b")));
        } catch (Exception e) {
            System.out.println("Somthing wrong :( " + e.toString());
        }

        driver.findElement(By.xpath("//ul[@id=\"langs\"]//li[1]//b")).click();
        driver.findElement(By.name("station_from")).sendKeys("Тернопіль");

        try {
            myDynamicElement.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[@id=\"ui-id-1\"]/li[contains(text(),'Тернопіль')][1]")));
        } catch (Exception e) {
            System.out.println("Somthing wrong :( " + e.toString());
        }

        driver.findElement(By.name("station_from")).sendKeys(Keys.ENTER);
        driver.findElement(By.name("station_till")).sendKeys("Київ");

        try {
            myDynamicElement.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[@id=\"ui-id-2\"]/li[contains(text(),\"Київ\")][1]")));
        } catch (Exception e) {
            System.out.println("Somthing wrong :( " + e.toString());
        }

        driver.findElement(By.name("station_till")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("date_dep")).click();
        driver.findElement(By.xpath("//td[@data-month='7']//a[text()='13']")).click();

        try {
            myDynamicElement.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//td[@data-month='7']//a[text()='13']")));
        } catch (Exception e) {
            System.out.println("Somthing wrong :( " + e.toString());
        }

        try {
            myDynamicElement.until(ExpectedConditions.elementToBeClickable(By.name("search")));
        } catch (Exception e) {
            System.out.println("Somthing wrong :( " + e.toString());
        }
        driver.findElement(By.name("search")).click();
        try {
            myDynamicElement.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//table[@id='ts_res_tbl']//td[@class='num']//a")));
        } catch (Exception e) {
            System.out.println("Somthing wrong :( " + e.toString());
        }
    }


    private void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
    }


    public void sendNotificationOnEmail() {
        Properties properties = getProperties();
        String username = properties.getProperty("email.username");
        String password = properties.getProperty("email.password");
        String recipientEmail = properties.getProperty("email.recipientEmail");
        String messagetitle = properties.getProperty("email.messagetitle");
        String message = properties.getProperty("email.message");
        try {
            GoogleMail.send(username, password, recipientEmail, messagetitle, message);
            System.out.println("Message sent on GMail");
        } catch (MessagingException e) {
            System.out.println("Can't send email. " + e.toString());
        }
        driver.quit();
    }


    private static Properties getProperties() {
        Properties properties = new Properties();
        FileInputStream in;
        try {
            in = new FileInputStream("config.properties");
            properties.load(in);
        } catch (IOException e) {
            System.out.println("Can't read properties " + e.toString());
        }
        return properties;
    }

    @AfterTest
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }


    }
}




