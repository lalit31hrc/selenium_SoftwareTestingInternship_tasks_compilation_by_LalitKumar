package DemoSuite;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
public class TestUserIsAbleToFillUpFormAsGuest {
	String url = "https://appointment.questdiagnostics.com/schedule-appointment/as-personal-information";
	WebDriver driver;

	@BeforeClass
	public void setup() {
		url = "https://appointment.questdiagnostics.com/schedule-appointment/as-personal-information";
		// Selenium 4 automatically manages ChromeDriver binary
		driver = new ChromeDriver();
		driver.get(url);
	}

	@Test
	public void fillUpForm() {
		driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler'][1]")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Continue as a guest\"]")));
		WebElement searchBox = driver.findElement(By.xpath("//button[@aria-label=\"Continue as a guest\"]"));
		searchBox.click();
		
		String AppointFormPageTitle=driver.getTitle();
		
		assertEquals(AppointFormPageTitle, "Schedule Appointment - Personal information", "Appointment Form Page Displayed");
		
		driver.findElement(By.id("firstName")).sendKeys("SampleFirst");
		driver.findElement(By.id("lastName")).sendKeys("SampleLast");
		driver.findElement(By.id("dateOfBirth")).sendKeys("01011998");
		
		driver.findElement(By.xpath("//div[@class='ds-input__radio'][1]")).click();
		
		WebElement gendropdown=driver.findElement(By.id("mat-select-0"));
		gendropdown.click();
		driver.findElement(By.id("mat-option-1")).click();
	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
