package com.ama.qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/* 
* Author Information:
* Author: Sonal Garg 
* LinkedIn: https://www.linkedin.com/in/sonalgarg32/
* 
* @version 1.0
* @since 2024-09-22
*/
public class TestBase {

	public static WebDriver driver = null;
	public static Properties prop;
	// public static EventFiringWebDriver e_driver;
	// public static WebEventListener eventListener;
	
	public TestBase() {
		try {
			prop = new Properties();
			prop.load(getClass().getClassLoader().getResourceAsStream("com/ama/qa/config/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//String browsername = prop.getProperty("browser");
		
	}

	public static void initialization() {
		String browsername = prop.getProperty("BROWSER");

		if (browsername.equals("chrome")) {
			driver = getdriver();

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(prop.getProperty("URL"));
	}

	/*public static void myWait()
	{
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(2000));
		//wait.until(ExpectedConditions.element))
	}
	*/
	public static final WebDriver getdriver(){
	    if (driver == null){
	      driver = new ChromeDriver();
	      return driver;
	    }else{
	      return driver;
	    }
	  }
	
	
}
