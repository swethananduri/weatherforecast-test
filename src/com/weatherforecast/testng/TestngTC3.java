package com.weatherforecast.testng;

import org.testng.annotations.Test;

import com.weatherforecast.test.utils.TestUtils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class TestngTC3 {

	WebDriver driver = null;
	List<WeatherForecastData> foreCastDataList = null;

	
	@DataProvider
	public Object[][] forecastDataProvider() {

		if(foreCastDataList == null ){
			
			String rootElementPath = "//*[@id='root']/div";

			foreCastDataList = new ArrayList<WeatherForecastData>();

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
					// Expands
					summaryRowSpan.click();
					WeatherForecastData forecastData = new WeatherForecastData();
					readForecastdata(forecastData, i);
					foreCastDataList.add(forecastData);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Collapses
					summaryRowSpan.click();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					i++;
				} else {
					elementExist = false;
				}

			}
		}
		

		Object[][] forecastData = new Object[foreCastDataList.size()][2];

		for (int i = 0; i < foreCastDataList.size(); i++) {
			forecastData[i][0] = i;
			forecastData[i][1] = foreCastDataList.get(i);
		}

		return forecastData;
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

	private void readForecastdata(WeatherForecastData forecastData, int elementIndex) {

		// Reading Summary
		WebElement summaryMaxTempElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[3]/span[1]"));

		String currentMaxTemp = summaryMaxTempElem.getText();
		currentMaxTemp = currentMaxTemp.substring(0, currentMaxTemp.length() - 1);

		WebElement summaryMinTempElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[3]/span[2]"));
		String currentMinTemp = summaryMinTempElem.getText();
		currentMinTemp = currentMinTemp.substring(0, currentMinTemp.length() - 1);

		
		forecastData.setMaxTemp((int)Math.floor(Double.parseDouble(currentMaxTemp)));
		forecastData.setMinTemp((int)Math.floor(Double.parseDouble(currentMinTemp)));

		// Reading Summary
		WebElement summaryWindSpeed = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[4]/span[1]"));

		String currentWindSpeed = summaryWindSpeed.getText();
		currentWindSpeed = currentWindSpeed.substring(0, currentWindSpeed.length() - 3);

		forecastData.setWindspeed((int)Math.floor(Double.parseDouble(currentWindSpeed)));

		// Reading Summary
		WebElement summaryRainFallElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[5]/span[1]"));

		String summaryRainFall = summaryRainFallElem.getText();
		summaryRainFall = summaryRainFall.substring(0, summaryRainFall.length() - 2);

		forecastData.setAggregatedRainFall((int)Math.floor(Double.parseDouble(summaryRainFall)));

		WebElement summaryPressueElem = driver
				.findElement(By.xpath("//*[@id='root']/div/div[" + elementIndex + "]/div[1]/span[5]/span[2]"));
		String summaryPressure = summaryPressueElem.getText();
		summaryPressure = summaryPressure.substring(0, summaryPressure.length() - 2);

		forecastData.setPressure((int)Math.floor(Double.parseDouble(summaryPressure)));

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
				forecastData.getHourInformation().add((int)Math.floor(Double.parseDouble(hourInfo)));

				String maxTemp = driver.findElement(By.xpath(detailElementPath + "/span[3]/span[1]")).getText();
				maxTemp = maxTemp.substring(0, maxTemp.length() - 1);

				String minTemp = driver.findElement(By.xpath(detailElementPath + "/span[3]/span[2]")).getText();
				minTemp = minTemp.substring(0, minTemp.length() - 1);

				forecastData.getHourlyMaxTemp().add((int)Math.floor(Double.parseDouble(maxTemp)));
				forecastData.getHourlyMinTemp().add((int)Math.floor(Double.parseDouble(minTemp)));

				String windSpeed = driver.findElement(By.xpath(detailElementPath + "/span[4]/span[1]")).getText();
				windSpeed = windSpeed.substring(0, windSpeed.length() - 3);
				forecastData.getHourlyWindSpeed().add((int)Math.floor(Double.parseDouble(windSpeed)));

				String rainFall = driver.findElement(By.xpath(detailElementPath + "/span[5]/span[1]")).getText();
				rainFall = rainFall.substring(0, rainFall.length() - 2);
				forecastData.getHourlyRainFall().add((int)Math.floor(Double.parseDouble(rainFall)));

				String pressure = driver.findElement(By.xpath(detailElementPath + "/span[5]/span[2]")).getText();
				pressure = pressure.substring(0, pressure.length() - 2);
				forecastData.getHourlyPressure().add((int)Math.floor(Double.parseDouble(pressure)));

			} else {
				detailElementExist = false;
			}

			i++;
		}

	}

	@Test(dataProvider = "forecastDataProvider")
	public void validateSummaryPressure(Integer i, WeatherForecastData forecastData) {
		
		Boolean passed = false;
		List<Integer> hourlyPressure = forecastData.getHourlyPressure();

		

		if (forecastData.getPressure() == hourlyPressure.get(0)) {
			passed =  Boolean.TRUE;
		}
		Assert.assertEquals(passed, Boolean.TRUE);
	}

	@Test(dataProvider = "forecastDataProvider")
	public void validateHourlyData(Integer i, WeatherForecastData forecastData) {

		Boolean passed = Boolean.TRUE;
		// it has all the hour information in screen rendering order, hence we
		// can compare previous to current.
		List<Integer> hourlyInfo = forecastData.getHourInformation();
		Integer prevHourInfo = null;
		for (Integer hourInfo : hourlyInfo) {
			if (prevHourInfo == null) {
				prevHourInfo = hourInfo;
			} else {
				if ((hourInfo - prevHourInfo) != 300) {
					passed = Boolean.FALSE;
					break;
				}

				prevHourInfo = hourInfo;
			}
		}
		Assert.assertEquals(passed, Boolean.TRUE);
	}

	@Test(dataProvider = "forecastDataProvider")
	public void validateSummaryRainFall(Integer i, WeatherForecastData forecastData) {
		Boolean passed = Boolean.FALSE;
		List<Integer> hourlyRainFall = forecastData.getHourlyRainFall();
		int currentAggrRainFall = 0;

		for (Integer rainfall : hourlyRainFall) {
			currentAggrRainFall += rainfall;
		}

		if (forecastData.getAggregatedRainFall() == currentAggrRainFall) {
			passed = Boolean.TRUE;
		}
		Assert.assertEquals(passed, Boolean.TRUE);
	}

	@Test(dataProvider = "forecastDataProvider")
	public void validateWindSpeed(Integer i, WeatherForecastData forecastData) {
		Boolean passed = Boolean.FALSE;
		List<Integer> hourlyWindSpeed = forecastData.getHourlyWindSpeed();

		// int currentMaxWindSpeed = Collections.max(hourlyWindSpeed);

		if (forecastData.getWindspeed() == hourlyWindSpeed.get(0)) {
			passed = Boolean.TRUE;
		}
		Assert.assertEquals(passed, Boolean.TRUE);
	}

	@Test(dataProvider = "forecastDataProvider")
	public void validateTemperature(Integer i, WeatherForecastData forecastData) {
		
		Boolean passed = Boolean.TRUE;
		List<Integer> hourlyMaxTemp = forecastData.getHourlyMaxTemp();

		int maxTemp = Collections.max(hourlyMaxTemp);

		if (forecastData.getMaxTemp() != maxTemp) {
			passed = Boolean.FALSE;
		}

		List<Integer> hourlyMinTemp = forecastData.getHourlyMinTemp();

		int minTemp = Collections.min(hourlyMinTemp);

		if (forecastData.getMinTemp() != minTemp) {
			passed = Boolean.FALSE;
		}
		Assert.assertEquals(passed, Boolean.TRUE);
	}

}

class WeatherForecastData {

	private Date date;
	private int maxTemp;
	private int minTemp;
	private int windspeed;
	private int maxWindspeedDirection;
	private int aggregatedRainFall;
	private int pressure;

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

	public int getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(int windspeed) {
		this.windspeed = windspeed;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

}
