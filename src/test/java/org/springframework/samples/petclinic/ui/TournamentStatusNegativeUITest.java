
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TournamentStatusNegativeUITest {

	@LocalServerPort
	private int				port;
	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {

		String value = System.getenv("webdriver.gecko.driver");
		System.setProperty("webdriver.gecko.driver", value);
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testTournamentStatusNegativeUI() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
		this.driver.findElement(By.linkText("Hability ConTEST")).click();
		this.driver.findElement(By.linkText("Add New Sponsor")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("luis");
		this.driver.findElement(By.id("money")).click();
		this.driver.findElement(By.id("money")).clear();
		this.driver.findElement(By.id("money")).sendKeys("7000.");
		this.driver.findElement(By.id("url")).click();
		this.driver.findElement(By.id("url")).clear();
		this.driver.findElement(By.id("url")).sendKeys("https://www.youtube.com/");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("Edit this Tournament")).click();
		new Select(this.driver.findElement(By.id("status"))).selectByVisibleText("PENDING");
		this.driver.findElement(By.xpath("//option[@value='PENDING']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//form[@id='hability']/div/div[7]/div")).click();
		Assert.assertEquals("No judges found for this competition", this.driver.findElement(By.xpath("//form[@id='hability']/div/div[7]/div/span[2]")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
