package org.springframework.samples.petclinic.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// Test UI de la historia de usuario #4 positivo

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MinimunDataForATournamentPositiveUITest {
	@LocalServerPort
  private int port;
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		String value = System.getenv("webdriver.gecko.driver");
		System.setProperty("webdriver.gecko.driver", value);
		driver = new FirefoxDriver();

		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

  @Test
  public void testHistoriaDeUsuario4() throws Exception {
	  driver.get("http://localhost:"+this.port+"/");
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.xpath("//div")).click();
	    driver.findElement(By.id("username")).sendKeys("admin1");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.xpath("//div")).click();
	    driver.findElement(By.id("password")).sendKeys("4dm1n");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span")).click();   
	    driver.findElement(By.xpath("//a[contains(text(),'Add\n					New Race')]")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Testing Race");
	    driver.findElement(By.id("name")).sendKeys(Keys.DOWN);
	    driver.findElement(By.id("name")).sendKeys(Keys.TAB);
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("Next")).click();
	    driver.findElement(By.linkText("8")).click();
	    driver.findElement(By.id("capacity")).click();
	    driver.findElement(By.id("capacity")).clear();
	    driver.findElement(By.id("capacity")).sendKeys("8000");
	    driver.findElement(By.id("rewardMoney")).click();
	    driver.findElement(By.id("rewardMoney")).clear();
	    driver.findElement(By.id("rewardMoney")).sendKeys("8000");
	    driver.findElement(By.id("capacity")).click();
	    driver.findElement(By.id("capacity")).clear();
	    driver.findElement(By.id("capacity")).sendKeys("200");
	    driver.findElement(By.id("canodrome")).click();
	    driver.findElement(By.id("canodrome")).clear();
	    driver.findElement(By.id("canodrome")).sendKeys("Gr");
	    driver.findElement(By.id("canodrome")).sendKeys(Keys.DOWN);
	    driver.findElement(By.id("canodrome")).clear();
	    driver.findElement(By.id("canodrome")).sendKeys("Great Canodrome of Testing");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Testing Race")).click();
	    driver.findElement(By.linkText("Add New Sponsor")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Sponsor Test");
	    driver.findElement(By.id("money")).click();
	    driver.findElement(By.id("money")).clear();
	    driver.findElement(By.id("money")).sendKeys("8000");
	    driver.findElement(By.id("url")).click();
	    driver.findElement(By.id("url")).clear();
	    driver.findElement(By.id("url")).sendKeys("http://www.google.es");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Add New Judge")).click();
	    driver.findElement(By.linkText("Juez Primero")).click();
	    assertEquals("Sponsor Test", driver.findElement(By.xpath("//table[3]/tbody/tr/td/dl/dd")).getText());
	    assertEquals("Juez Primero", driver.findElement(By.xpath("//table[4]/tbody/tr/td/dl/dd")).getText());
	    assertEquals("Great Canodrome of Testing", driver.findElement(By.xpath("//tr[8]/td")).getText());
	    assertEquals("2800.0 EUR", driver.findElement(By.xpath("//tr[6]/td")).getText());
	    assertEquals("1200.0 EUR", driver.findElement(By.xpath("//tr[7]/td")).getText());
	    assertEquals("200 spectators", driver.findElement(By.xpath("//tr[3]/td")).getText());  }

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

