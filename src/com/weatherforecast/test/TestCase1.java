package com.weatherforecast.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.weatherforecast.test.utils.TestUtils;

public class TestCase1 {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", TestUtils.getDriverPath("driver.chrome.path"));
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:3000/");
		driver.findElement(By.id("city")).clear();
		driver.findElement(By.id("city")).sendKeys("Glasgow");
		driver.findElement(By.id("city")).sendKeys(Keys.ENTER);

	}

}
