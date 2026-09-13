package com.ama.qa.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
public class AmazonProductCheckoutPage extends TestBase{
	@FindBy(xpath = "//span[@id='shipToThisAddressButton']")WebElement shipToAddressButton;
	
	public AmazonProductCheckoutPage(){
		PageFactory.initElements(getdriver(), this);
	}
 
	public void clickShipToAddressButton()
	{
	    if (getdriver().getCurrentUrl().contains("oos")) {
	        throw new org.testng.SkipException("Checkout blocked: selected product is unavailable/restricted from this seller (Amazon OOS page). This is a live-inventory/seller limitation, not a code defect.");
	    }
	    shipToAddressButton.click();
	}
}
