package cphbusiness.test.ex.selenium;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import com.jayway.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumCarTest {

	private static WebDriver driver;

	private static final int TIMETOWAIT = 4;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\user\\Drivers\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Drivers\\chromedriver.exe");

		RestAssured.given().get("http://localhost:3000/reset");
		driver = new ChromeDriver();
		driver.get("http://localhost:3000");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
		RestAssured.given().get("http://localhost:3000/reset");
	}

	@Test
	public void test1() throws Exception {
		(new WebDriverWait(driver, TIMETOWAIT)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
			WebElement e = d.findElement(By.tagName("tbody"));
			List<WebElement> rows = e.findElements(By.tagName("tr"));
			assertThat(rows.size(), is(5));
			return true;
		});
	}

	@Test
	public void test2() throws Exception {
		WebElement element = driver.findElement(By.id("filter"));
		(new WebDriverWait(driver, TIMETOWAIT)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
			element.sendKeys("2002");
			WebElement e = d.findElement(By.tagName("tbody"));
			List<WebElement> rows = e.findElements(By.tagName("tr"));
			assertThat(rows.size(), is(2));
			return true;
		});
	}

	@Test
	public void test3() throws Exception {

	}

}
