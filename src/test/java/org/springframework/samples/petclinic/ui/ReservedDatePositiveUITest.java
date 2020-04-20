package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservedDatePositiveUITest {
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
	public void testReservedDateExceptionNegativeUI() throws Exception {
		driver.get("http://localhost:"+port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin1");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Add\n					New Race')]")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Date Exception Positive");
		driver.findElement(By.id("date")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		driver.findElement(By.linkText("26")).click();
		driver.findElement(By.id("capacity")).click();
		driver.findElement(By.id("capacity")).clear();
		driver.findElement(By.id("capacity")).sendKeys("1000");
		new Select(driver.findElement(By.id("breedRestriction"))).selectByVisibleText("Greyhound");
		driver.findElement(By.xpath("//option[@value='Greyhound']")).click();
		driver.findElement(By.id("rewardMoney")).click();
		driver.findElement(By.id("rewardMoney")).clear();
		driver.findElement(By.id("rewardMoney")).sendKeys("1000");
		driver.findElement(By.id("canodrome")).click();
		driver.findElement(By.id("canodrome")).clear();
		driver.findElement(By.id("canodrome")).sendKeys("Martín Carpena");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Date Exception Positive", driver.findElement(By.linkText("Date Exception Positive")).getText());
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

