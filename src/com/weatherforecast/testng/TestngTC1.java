package com.weatherforecast.testng;

import org.testng.annotations.Test;

import com.weatherforecast.test.utils.TestUtils;

import org.testng.annotations.DataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestngTC1 {

	WebDriver driver = null;;
	
	@Test(dataProvider = "cityDataProvider")
	public void executeTest(Integer n, String city) {
		Boolean passed = Boolean.TRUE;
		try{
			driver.findElement(By.id("city")).clear();
			driver.findElement(By.id("city")).sendKeys(city);
			driver.findElement(By.id("city")).sendKeys(Keys.ENTER);
			Pattern pattern = Pattern.compile("[a-zA-Z]*");
			Matcher matcher = pattern.matcher(city);
			if (!matcher.matches()) {
				passed = Boolean.FALSE;
			}
		}catch(Exception exception){
			 passed = Boolean.FALSE;
		}
		
	    Assert.assertEquals(passed, Boolean.TRUE);
	}

	@DataProvider
	public Object[][] cityDataProvider() {

		return new Object[][] { new Object[] { 1, "Glasgow" }, new Object[] { 2, "Aberdeen"} , new Object[] { 3, "Hyderabad"} ,new Object[] { 4, "Dundee"} ,new Object[] { 5, "Edinburgh"} ,new Object[] { 6, "Perth"} ,new Object[] { 7, "Stirling"}, new Object[] { 8, "jsa#@#878"}};
	}
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", TestUtils.getDriverPath("driver.chrome.path"));
		driver = new ChromeDriver();
		driver.get("http://localhost:3000/");
	}

	@AfterTest
	public void closeBrowser(){
		driver.close();
	}
	
}
