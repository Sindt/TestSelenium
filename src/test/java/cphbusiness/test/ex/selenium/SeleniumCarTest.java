package cphbusiness.test.ex.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import com.jayway.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

		(new WebDriverWait(driver, TIMETOWAIT)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
			WebElement element = d.findElement(By.id("filter"));
			element.sendKeys("2002");
			WebElement e = d.findElement(By.tagName("tbody"));
			List<WebElement> rows = e.findElements(By.tagName("tr"));
			assertThat(rows.size(), is(2));
			return true;
		});
	}

	@Test
	public void test3() throws Exception {
		(new WebDriverWait(driver, TIMETOWAIT)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
			WebElement element = d.findElement(By.id("filter"));
			element.clear();
			element.sendKeys(" ");
			WebElement e = d.findElement(By.tagName("tbody"));
			List<WebElement> rows = e.findElements(By.tagName("tr"));
			assertThat(rows.size(), is(5));
			return true;
		});
	}

	@Test
	public void test4() throws Exception {
		driver.findElement(By.id("h_year")).click();

		int first = driver.findElements(By.xpath("//tbody/tr[1 and td = '938']")).size();
		int second = driver.findElements(By.xpath("//tbody/tr[5 and td = '940']")).size();

		assertThat(first, is(1));
		assertThat(second, is(1));
	}

	@Test
	public void test5() throws Exception {
		WebElement rowEle = driver.findElement(By.xpath("//tbody/tr[td='938']"));
		rowEle.findElements(By.tagName("a")).get(0).click();

		WebElement des = driver.findElement(By.name("description"));
		des.clear();
		des.sendKeys("Cool car");

		driver.findElement(By.id("save")).click();

		(new WebDriverWait(driver, TIMETOWAIT)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
			WebElement newRow = d.findElement(By.xpath("//tbody/tr[td = '938']"));
			String result = newRow.findElements(By.tagName("td")).get(5).getText();
			assertThat("Cool car", is(equalTo(result)));
			return true;
		});
	}

	@Test
	public void test6() throws Exception {
		driver.findElement(By.id("new")).click();
		driver.findElement(By.id("save")).click();
		List<WebElement> rows = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		String result = driver.findElement(By.id("submiterr")).getText();
		
		assertThat(rows.size(), is(5));
		assertThat("All fields are required", is(equalTo(result)));
	}

}
