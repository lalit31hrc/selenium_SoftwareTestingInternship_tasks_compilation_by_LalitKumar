package com.ama.qa.pages;

import java.time.Duration;
import java.util.Set;

import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ama.qa.base.TestBase;

/* 
* Author Information:
* Author: Sonal Garg 
* LinkedIn: https://www.linkedin.com/in/sonalgarg32/
* 
* @version 1.0
* @since 2024-09-22
*/
public class AmazonSearchProductsPage extends TestBase {

	@FindBy(xpath = "//span[@class='a-color-state a-text-bold']")
	WebElement searchedProductsPageLabel;
	WebElement serachProductPanel;

	// Create WebDriverWait instance
	WebDriverWait wait = new WebDriverWait(getdriver(), Duration.ofSeconds(20));
	Set<String> newWindows;
	String oldWindow;

	public AmazonSearchProductsPage() {
		PageFactory.initElements(getdriver(), this);
	}

	public String getPageLabelForSearchedProduct() {
		return searchedProductsPageLabel.getText();
	}

	public void clickOnProduct(String productName) {
	    WebElement productLink = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.xpath(
	            	 "//div[@data-component-type='s-search-result']//a[.//h2//span[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
	            	                + productName.toLowerCase() + "')]]")));

	    oldWindow = getdriver().getWindowHandle();
	    productLink.click();

	    try {
	        new WebDriverWait(getdriver(), Duration.ofSeconds(5))
	            .until(driver -> driver.getWindowHandles().size() > 1);

	        newWindows = getdriver().getWindowHandles();
	        for (String window : newWindows) {
	            if (!oldWindow.equalsIgnoreCase(window)) {
	                getdriver().switchTo().window(window);
	                break;
	            }
	        }
	    } catch (org.openqa.selenium.TimeoutException e) {
	        // Same-tab navigation — nothing more to do
	    }
	}
}
