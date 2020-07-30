package com.weatherforecast.test;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.weatherforecast.test.utils.TestUtils;

/*public class TestCase1 {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", TestUtils.getDriverPath("driver.chrome.path"));
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:3000/");
		driver.findElement(By.id("city")).clear();
		driver.findElement(By.id("city")).sendKeys("Glasgow");
		driver.findElement(By.id("city")).sendKeys(Keys.ENTER);*/
public class TestCase1 {
public static void main(String []args)throws Exception{
	System.setProperty("webdriver.chrome.driver", TestUtils.getDriverPath("driver.chrome.path"));
	WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get("http://localhost:3000/");
    driver.findElement(By.id("city")).clear();
    driver.findElement(By.id("city")).sendKeys("#!#!#$@#!$@!$@#$%#%^#$^^&%&$%*");
	driver.findElement(By.id("city")).sendKeys(Keys.ENTER);
    String city = driver.findElement(By.id("city")).getText();
    Pattern pattern = Pattern.compile("[a-zA-Z]*");
    Matcher matcher = pattern.matcher(city);
    Boolean Passed = Boolean.TRUE;
    if(matcher.matches()){
    	Passed = Boolean.FALSE;
    }
	Assert.assertEquals(Passed, Boolean.TRUE);
   


}
}

