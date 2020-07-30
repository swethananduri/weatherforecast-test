package com.weatherforecast.testng;

import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.weatherforecast.test.utils.TestUtils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

/*
 * This Test case validates whether the click on summary item is working or not
 * 
 * Select day, get 3 hourly forecast
   Select day again, hide 3 hourly forecast
 */
public class TestngTC2 {
	WebDriver driver = null;

	@Test
	public void verifyHourlyDataOnDay() {
		String rootElementPath = "//*[@id='root']/div";

		boolean elementExist = true;
		int i = 1;
		Boolean passed = false;
		while (elementExist) {

			String childSpanPath = rootElementPath + "/div[" + i + "]/div[1]/span[1]";
			WebElement childSpan = null;

			try {
				childSpan = driver.findElement(By.xpath(childSpanPath));
			} catch (Exception exception) {

			}

			if (childSpan != null) {
				childSpan.click();
				WebElement detailElement = driver.findElement(By.xpath(rootElementPath + "/div[" + i + "]/div[2]"));
				String attrib = detailElement.getAttribute("style");
				if (attrib != null && attrib.contains("max-height: 2000px")) {
					passed = true;
				} else {
					passed = false;
				}

				childSpan.click();
				i++;
				if (!passed) {
					break;
				}
			} else {
				elementExist = false;
			}

		}
		
		Assert.assertEquals(passed, Boolean.TRUE);
	}

	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", TestUtils.getDriverPath("driver.chrome.path"));
		driver = new ChromeDriver();
		driver.get("http://localhost:3000/");
	}

	@AfterTest
	public void closeBrowser() {
		driver.close();
	}

}
