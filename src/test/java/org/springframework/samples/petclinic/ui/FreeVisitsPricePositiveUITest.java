package org.springframework.samples.petclinic.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FreeVisitsPricePositiveUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @LocalServerPort
  private int port;
  
  @BeforeEach
  public void setUp() throws Exception {
	String value = System.getenv("webdriver.gecko.driver");
	System.setProperty("webdriver.gecko.driver", value);
	
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }


  @org.junit.jupiter.api.Test
  public void testFreeVisitsPricePositiveUI() throws Exception {
	driver.get("http://localhost:" + this.port);
	driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	driver.findElement(By.id("username")).clear();
	driver.findElement(By.id("username")).sendKeys("admin1");
	driver.findElement(By.id("password")).click();
	driver.findElement(By.id("password")).clear();
	driver.findElement(By.id("password")).sendKeys("4dm1n");
	driver.findElement(By.xpath("//button[@type='submit']")).click();
    
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
    driver.findElement(By.linkText("First Race Test")).click();
    driver.findElement(By.linkText("Show results")).click();
    assertEquals("Lucky", driver.findElement(By.xpath("//table[@id='raceResultsTable']/tbody/tr/td[2]")).getText());
    
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Jeff Black")).click();
    driver.findElement(By.linkText("Add Visit")).click();
    driver.findElement(By.xpath("//h2[2]")).click();
    String freeVisit = driver.findElement(By.xpath("//h2[2]")).getText();
    assertEquals(freeVisit, "THIS IS A FREE VISIT");
  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
