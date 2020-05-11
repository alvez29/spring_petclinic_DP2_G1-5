package org.springframework.samples.petclinic.ui;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//Prueba 9

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardPercentagePositiveUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  
  @LocalServerPort
  private int port;
  
  @BeforeEach
  public void setUp() throws Exception {
	String value = System.getenv("webdriver.gecko.driver");		
	System.setProperty("webdriver.gecko.driver", value );
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPremiosDivididos() throws Exception {
	driver.get("http://localhost:" + this.port);
	driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	driver.findElement(By.id("username")).clear();
	driver.findElement(By.id("username")).sendKeys("admin1");
	driver.findElement(By.id("password")).click();
	driver.findElement(By.id("password")).clear();
	driver.findElement(By.id("password")).sendKeys("4dm1n");
	driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
    driver.findElement(By.linkText("Hability ConTEST")).click();
    driver.findElement(By.xpath("//tr[5]/td")).click();
    String reward1 = driver.findElement(By.xpath("//tr[5]/td")).getText();
    driver.findElement(By.xpath("//tr[6]/td")).click();
    String reward2 = driver.findElement(By.xpath("//tr[6]/td")).getText();
    driver.findElement(By.xpath("//tr[7]/td")).click();
    String reward3 = driver.findElement(By.xpath("//tr[7]/td")).getText();
    driver.findElement(By.linkText("Edit this Tournament")).click();
    driver.findElement(By.id("rewardMoney")).click();
    String totalMoney = driver.findElement(By.id("rewardMoney")).getAttribute("value");
    
    String reward1Trozos[] = reward1.split(" ");
    String reward2Trozos[] = reward2.split(" ");
    String reward3Trozos[] = reward3.split(" ");
    
    
    Double r1 = Double.parseDouble(reward1Trozos[0]);
    Double r2 = Double.parseDouble(reward2Trozos[0]);
    Double r3 = Double.parseDouble(reward3Trozos[0]);
    Double t = Double.parseDouble(totalMoney);
    
    assertThat(t*50/100, IsEqual.equalTo(r1));
    assertThat(t*35/100, IsEqual.equalTo(r2));
    assertThat(t*15/100, IsEqual.equalTo(r3));
    
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

