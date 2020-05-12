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

// Test 15

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetWithVisitPeriodPositiveUITest {

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
	public void petWithVisitPeriodPositiveUITest() throws Exception {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.id("username")).click();
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
		driver.findElement(By.id("name")).sendKeys("Period Month Race");
		driver.findElement(By.id("date")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		driver.findElement(By.linkText("1")).click();
		driver.findElement(By.id("capacity")).sendKeys("800");
		driver.findElement(By.id("rewardMoney")).sendKeys("1000");
		driver.findElement(By.id("canodrome")).sendKeys("Great Canodrome of Testing");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Period Month Race", driver.findElement(By.linkText("Period Month Race")).getText());
		driver.findElement(By.linkText("Period Month Race")).click();
		driver.findElement(By.linkText("Add New Dog")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("Harold Davis")).click();
		driver.findElement(By.xpath("//dd")).click();
		String dogToTest = driver.findElement(By.xpath("//dd")).getText();
		driver.findElement(By.linkText("Add Visit")).click();
		driver.findElement(By.id("description")).click();
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("Visit to test");
		driver.findElement(By.id("clinic")).click();
		driver.findElement(By.id("clinic")).clear();
		driver.findElement(By.id("clinic")).sendKeys("Canin Vet");
		new Select(driver.findElement(By.id("competitionCheck"))).selectByVisibleText("PASSED");
		driver.findElement(By.xpath("//option[@value='PASSED']")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//td[4]")).click();
		driver.findElement(By.xpath("//td[4]")).click();
		driver.findElement(By.xpath("//td[4]")).click();
		driver.findElement(By.xpath("//td[4]")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [doubleClick | //td[4] |
		// ]]
		driver.findElement(By.xpath("//td[4]")).click();
		driver.findElement(By.xpath("//td[4]")).click();
		driver.findElement(By.xpath("//td[4]")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [doubleClick | //td[4] |
		// ]]
		assertEquals("PASSED", driver.findElement(By.xpath("//td[4]")).getText());
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
		driver.findElement(By.linkText("Period Month Race")).click();
		driver.findElement(By.linkText("Add New Dog")).click();
		driver.findElement(By.xpath("//table[@id='petTable']/tbody/tr/td")).click();
		assertEquals("Yorkshire: Iggy - Owner: (Harold Davis)",
				driver.findElement(By.linkText("Yorkshire: Iggy - Owner: (Harold Davis)")).getText());
		driver.findElement(By.linkText("Yorkshire: Iggy - Owner: (Harold Davis)")).click();
		driver.findElement(By.xpath("//dt")).click();
		assertEquals("Harold Davis", driver.findElement(By.xpath("//dd[3]")).getText());
		driver.findElement(By.xpath("//body/div")).click();
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
