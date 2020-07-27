package com.weatherforecast.test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.weatherforecast.test.utils.TestUtils;

/*
 * This Test case validates whether the click on summary item is working or not
 * 
 * Select day, get 3 hourly forecast
   Select day again, hide 3 hourly forecast
 */
public class TestCase2 {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", TestUtils.getDriverPath("driver.chrome.path"));
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:3000/");
		// To find the day and click on day:

		String rootElementPath = "//*[@id='root']/div";

		boolean elementExist = true;
		int i = 1;

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
					System.out.println("Test Case passed");
				} else {
					System.out.println("Test Case failed");
				}
				Thread.sleep(1000);
				childSpan.click();
				Thread.sleep(1000);
				i++;
			} else {
				elementExist = false;
			}

		}

	}
}
