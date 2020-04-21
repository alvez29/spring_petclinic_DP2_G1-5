
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
public class TournamentRegisterPositiveUITest {

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
	public void testTournamentRegisterPositiveUI() throws Exception {
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
		this.driver.findElement(By.xpath("//a[contains(text(),'Add\n					New Race')]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Race prueba");
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.linkText("6")).click();
		this.driver.findElement(By.id("capacity")).click();
		new Select(this.driver.findElement(By.id("breedRestriction"))).selectByVisibleText("Basset Hound");
		this.driver.findElement(By.xpath("//option[@value='Basset Hound']")).click();
		this.driver.findElement(By.id("capacity")).click();
		this.driver.findElement(By.id("capacity")).clear();
		this.driver.findElement(By.id("capacity")).sendKeys("800");
		this.driver.findElement(By.xpath("//option[@value='Basset Hound']")).click();
		this.driver.findElement(By.id("rewardMoney")).click();
		this.driver.findElement(By.id("rewardMoney")).clear();
		this.driver.findElement(By.id("rewardMoney")).sendKeys("700.");
		this.driver.findElement(By.id("canodrome")).click();
		this.driver.findElement(By.id("canodrome")).clear();
		this.driver.findElement(By.id("canodrome")).sendKeys("race place");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//table[@id='tournamentsTable']/tbody/tr[6]/td")).click();
		Assert.assertEquals("Race prueba", this.driver.findElement(By.linkText("Race prueba")).getText());
		this.driver.findElement(By.xpath("//a[contains(text(),'Add\n					New Beauty Contest')]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Beauty test");
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.linkText("13")).click();
		this.driver.findElement(By.id("capacity")).click();
		this.driver.findElement(By.id("capacity")).clear();
		this.driver.findElement(By.id("capacity")).sendKeys("800");
		new Select(this.driver.findElement(By.id("breedRestriction"))).selectByVisibleText("Basset Hound");
		this.driver.findElement(By.xpath("//option[@value='Basset Hound']")).click();
		this.driver.findElement(By.id("rewardMoney")).click();
		this.driver.findElement(By.id("rewardMoney")).clear();
		this.driver.findElement(By.id("rewardMoney")).sendKeys("700.");
		this.driver.findElement(By.id("place")).click();
		this.driver.findElement(By.id("place")).clear();
		this.driver.findElement(By.id("place")).sendKeys("Beauty place");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//table[@id='tournamentsTable']/tbody/tr[7]/td")).click();
		Assert.assertEquals("Beauty test", this.driver.findElement(By.linkText("Beauty test")).getText());
		this.driver.findElement(By.xpath("//a[contains(text(),'Add\n					New Hability Contest')]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Hability UITest");
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.linkText("19")).click();
		this.driver.findElement(By.id("capacity")).click();
		this.driver.findElement(By.id("capacity")).clear();
		this.driver.findElement(By.id("capacity")).sendKeys("800");
		new Select(this.driver.findElement(By.id("breedRestriction"))).selectByVisibleText("Beagle");
		this.driver.findElement(By.xpath("//option[@value='Beagle']")).click();
		this.driver.findElement(By.id("rewardMoney")).click();
		this.driver.findElement(By.id("rewardMoney")).clear();
		this.driver.findElement(By.id("rewardMoney")).sendKeys("700.");
		this.driver.findElement(By.id("circuit")).click();
		this.driver.findElement(By.id("circuit")).clear();
		this.driver.findElement(By.id("circuit")).sendKeys("Hability place");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//table[@id='tournamentsTable']/tbody/tr[8]/td")).click();
		Assert.assertEquals("Hability UITest", this.driver.findElement(By.linkText("Hability UITest")).getText());
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
