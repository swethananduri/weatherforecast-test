/**
 * 
 */
package com.weatherforecast.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.weatherforecast.test.utils.TestUtils;

/**
 * Verifying Summary data with hourly data Daily forecast should summarise the 3
 * hour data: Most dominant (or current) condition Most dominant (or current)
 * wind speed and direction Aggregate rainfall Minimum and maximum temperatures
 *
 */
public class TestCase3 {

	/**
	 * @param args
	 * @throws InterruptedException
	 */

	private static WebDriver driver = null;

	public static void main(String[] args) throws InterruptedException {
		
		setUp();
		driver.get("http://localhost:3000/");
		executeTests();
		
	}

	private static void setUp() {
		System.setProperty("webdriver.chrome.driver", TestUtils.getDriverPath("driver.chrome.path"));
		driver = new ChromeDriver();		
	}

	private static void executeTests() throws InterruptedException {
		
		String rootElementPath = "//*[@id='root']/div";

		boolean elementExist = true;
		int i = 1;

		while (elementExist) {

			String summaryRowPath = rootElementPath + "/div[" + i + "]/div[1]/span[1]";
			WebElement summaryRowSpan = null;

			try {
				summaryRowSpan = driver.findElement(By.xpath(summaryRowPath));
			} catch (Exception exception) {

			}

			if (summaryRowSpan != null) {
				//Expands
				summaryRowSpan.click();
				WeatherForecastData forecastData = new WeatherForecastData();
				readForecastdata(forecastData, i);
				String day = driver.findElement(By.xpath(summaryRowPath + "/span[1]")).getText();
				String date = driver.findElement(By.xpath(summaryRowPath + "/span[2]")).getText();
				System.out.println("Test Results For Summary for day :" + (day + " " + date));
				boolean isValidHourlyData = validateHourlyData(forecastData);

				boolean isValidSummaryTemp = validateTemperature(forecastData);

				boolean isValidSummaryWindSpeed = validateWindSpeed(forecastData);
				boolean isValidSummaryRainFall = validateSummaryRainFall(forecastData);

				boolean isValidSummaryPressure = validateSummaryPressure(forecastData);
				
				logTestCaseResultMessage("Hourly data Test Case", isValidHourlyData);
				logTestCaseResultMessage("Temperature Data Test case", isValidSummaryTemp);
				logTestCaseResultMessage("Windspeed Test case", isValidSummaryWindSpeed);
				logTestCaseResultMessage("Rainfall Test case", isValidSummaryRainFall);
				logTestCaseResultMessage("Pressure Test case", isValidSummaryPressure);
				System.out.println("");
				
				Thread.sleep(1000);
				//Collapses
				summaryRowSpan.click();
				Thread.sleep(1000);
				i++;
			} else {
				elementExist = false;
			}

		}		
	}

	private static void readForecastdata(WeatherForecastData forecastData, int elementIndex) {

		// Reading Summary
		WebElement summaryMaxTempElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[3]/span[1]"));

		String currentMaxTemp = summaryMaxTempElem.getText();
		currentMaxTemp = currentMaxTemp.substring(0, currentMaxTemp.length() - 1);

		WebElement summaryMinTempElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[3]/span[2]"));
		String currentMinTemp = summaryMinTempElem.getText();
		currentMinTemp = currentMinTemp.substring(0, currentMinTemp.length() - 1);

		forecastData.setMaxTemp(Integer.parseInt(currentMaxTemp));
		forecastData.setMinTemp(Integer.parseInt(currentMinTemp));

		// Reading Summary
		WebElement summaryWindSpeed = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[4]/span[1]"));

		String currentWindSpeed = summaryWindSpeed.getText();
		currentWindSpeed = currentWindSpeed.substring(0, currentWindSpeed.length() - 3);

		forecastData.setMaxWindspeed(Integer.parseInt(currentWindSpeed));

		// Reading Summary
		WebElement summaryRainFallElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[5]/span[1]"));

		String summaryRainFall = summaryRainFallElem.getText();
		summaryRainFall = summaryRainFall.substring(0, summaryRainFall.length() - 2);

		forecastData.setAggregatedRainFall(Integer.parseInt(summaryRainFall));

		WebElement summaryPressueElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[5]/span[2]"));
		String summaryPressure = summaryPressueElem.getText();
		summaryPressure = summaryPressure.substring(0, summaryPressure.length() - 2);

		forecastData.setMaxPressure(Integer.parseInt(summaryPressure));

		int i = 1;
		boolean detailElementExist = true;
		while (detailElementExist) {

			String detailElementPath = "//*[@id='root']/div/div[" + elementIndex + "]/div[2]/div[" + i + "]";
			WebElement detailElement = null;

			try {
				detailElement = driver.findElement(By.xpath(detailElementPath));
			} catch (Exception exception) {

			}

			if (detailElement != null) {

				String hourInfo = driver.findElement(By.xpath(detailElementPath + "/span[1]/span")).getText();
				forecastData.getHourInformation().add(Integer.parseInt(hourInfo));

				String maxTemp = driver.findElement(By.xpath(detailElementPath + "/span[3]/span[1]")).getText();
				maxTemp = maxTemp.substring(0, maxTemp.length() - 1);

				String minTemp = driver.findElement(By.xpath(detailElementPath + "/span[3]/span[2]")).getText();
				minTemp = minTemp.substring(0, minTemp.length() - 1);

				forecastData.getHourlyMaxTemp().add(Integer.parseInt(maxTemp));
				forecastData.getHourlyMinTemp().add(Integer.parseInt(minTemp));

				String windSpeed = driver.findElement(By.xpath(detailElementPath + "/span[4]/span[1]")).getText();
				windSpeed = windSpeed.substring(0, windSpeed.length() - 3);
				forecastData.getHourlyWindSpeed().add(Integer.parseInt(windSpeed));

				String rainFall = driver.findElement(By.xpath(detailElementPath + "/span[5]/span[1]")).getText();
				rainFall = rainFall.substring(0, rainFall.length() - 2);
				forecastData.getHourlyRainFall().add(Integer.parseInt(rainFall));

				String pressure = driver.findElement(By.xpath(detailElementPath + "/span[5]/span[2]")).getText();
				pressure = pressure.substring(0, pressure.length() - 2);
				forecastData.getHourlyPressure().add(Integer.parseInt(pressure));

			} else {
				detailElementExist = false;
			}

			i++;
		}

	}


	private static boolean validateSummaryPressure(WeatherForecastData forecastData) {
		List<Integer> hourlyPressure = forecastData.getHourlyPressure();

		int currentMaxpressure = Collections.max(hourlyPressure);

		if (forecastData.getMaxPressure() == currentMaxpressure) {
			return true;
		}
		return false;
	}

	private static boolean validateHourlyData(WeatherForecastData forecastData) {

		// it has all the hour information in screen rendering order, hence we
		// can compare previous to current.
		List<Integer> hourlyInfo = forecastData.getHourInformation();
		Integer prevHourInfo = null;
		for (Integer hourInfo : hourlyInfo) {
			if (prevHourInfo == null) {
				prevHourInfo = hourInfo;
			} else {
				if ((hourInfo - prevHourInfo) != 300) {
					return false;
				}

				prevHourInfo = hourInfo;
			}
		}
		return true;
	}

	private static boolean validateSummaryRainFall(WeatherForecastData forecastData) {
		List<Integer> hourlyRainFall = forecastData.getHourlyRainFall();
		int currentAggrRainFall = 0;

		for (Integer rainfall : hourlyRainFall) {
			currentAggrRainFall += rainfall;
		}

		if (forecastData.getAggregatedRainFall() == currentAggrRainFall) {
			return true;
		}
		return false;
	}

	private static boolean validateWindSpeed(WeatherForecastData forecastData) {
		List<Integer> hourlyWindSpeed = forecastData.getHourlyWindSpeed();

		int currentMaxWindSpeed = Collections.max(hourlyWindSpeed);

		if (forecastData.getMaxWindspeed() == currentMaxWindSpeed) {
			return true;
		}
		return false;
	}

	private static boolean validateTemperature(WeatherForecastData forecastData) {
		List<Integer> hourlyMaxTemp = forecastData.getHourlyMaxTemp();

		int maxTemp = Collections.max(hourlyMaxTemp);

		if (forecastData.getMaxTemp() != maxTemp) {
			return false;
		}

		List<Integer> hourlyMinTemp = forecastData.getHourlyMinTemp();

		int minTemp = Collections.min(hourlyMinTemp);

		if (forecastData.getMinTemp() != minTemp) {
			return false;
		}
		return true;
	}
	
	public static void logTestCaseResultMessage(String message, boolean passed){
		System.out.println(message + " : " + ( passed ? "Passed" : "Failed"));
	}

}

class WeatherForecastData {

	private Date date;
	private int maxTemp;
	private int minTemp;
	private int maxWindspeed;
	private int maxWindspeedDirection;
	private int aggregatedRainFall;
	private int maxPressure;

	private List<Integer> hourlyMaxTemp = new ArrayList<Integer>();
	private List<Integer> hourlyMinTemp = new ArrayList<Integer>();;

	private List<Integer> hourlyWindSpeed = new ArrayList<Integer>();;
	private List<Integer> hourlyRainFall = new ArrayList<Integer>();;
	private List<Integer> hourlyPressure = new ArrayList<Integer>();;

	private List<Integer> hourInformation = new ArrayList<Integer>();;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}

	public int getMaxWindspeed() {
		return maxWindspeed;
	}

	public void setMaxWindspeed(int maxWindspeed) {
		this.maxWindspeed = maxWindspeed;
	}

	public int getMaxWindspeedDirection() {
		return maxWindspeedDirection;
	}

	public void setMaxWindspeedDirection(int maxWindspeedDirection) {
		this.maxWindspeedDirection = maxWindspeedDirection;
	}

	public int getAggregatedRainFall() {
		return aggregatedRainFall;
	}

	public void setAggregatedRainFall(int aggregatedRainFall) {
		this.aggregatedRainFall = aggregatedRainFall;
	}

	public int getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(int maxPressure) {
		this.maxPressure = maxPressure;
	}

	public List<Integer> getHourlyWindSpeed() {
		return hourlyWindSpeed;
	}

	public void setHourlyWindSpeed(List<Integer> hourlyWindSpeed) {
		this.hourlyWindSpeed = hourlyWindSpeed;
	}

	public List<Integer> getHourlyPressure() {
		return hourlyPressure;
	}

	public void setHourlyPressure(List<Integer> hourlyPressure) {
		this.hourlyPressure = hourlyPressure;
	}

	public List<Integer> getHourInformation() {
		return hourInformation;
	}

	public void setHourInformation(List<Integer> hourInformation) {
		this.hourInformation = hourInformation;
	}

	public List<Integer> getHourlyMaxTemp() {
		return hourlyMaxTemp;
	}

	public void setHourlyMaxTemp(List<Integer> hourlyMaxTemp) {
		this.hourlyMaxTemp = hourlyMaxTemp;
	}

	public List<Integer> getHourlyMinTemp() {
		return hourlyMinTemp;
	}

	public void setHourlyMinTemp(List<Integer> hourlyMinTemp) {
		this.hourlyMinTemp = hourlyMinTemp;
	}

	public List<Integer> getHourlyRainFall() {
		return hourlyRainFall;
	}

	public void setHourlyRainFall(List<Integer> hourlyRainFall) {
		this.hourlyRainFall = hourlyRainFall;
	}
	

}
