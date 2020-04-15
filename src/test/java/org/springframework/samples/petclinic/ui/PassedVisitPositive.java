package org.springframework.samples.petclinic.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PassedVisitPositive {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {	
		String pathToGeckoDriver="C:\\Users\\pablo\\OneDrive\\Documentos\\Universidad\\3º\\DP II";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver+ "\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	  @Test
	  public void passedVisitPositive() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("vet1");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("v3t");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("George Franklin")).click();
	    driver.findElement(By.linkText("Add Visit")).click();
	    driver.findElement(By.id("description")).click();
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("Positive UI Test");
	    driver.findElement(By.id("clinic")).click();
	    driver.findElement(By.id("clinic")).clear();
	    driver.findElement(By.id("clinic")).sendKeys("Canin Vet");
	    new Select(driver.findElement(By.id("competitionCheck"))).selectByVisibleText("PASSED");
	    driver.findElement(By.xpath("//option[@value='PASSED']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("PASSED", driver.findElement(By.xpath("//td[4]")).getText());
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
