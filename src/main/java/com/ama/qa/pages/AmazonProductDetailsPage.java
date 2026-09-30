package com.ama.qa.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
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
public class AmazonProductDetailsPage extends TestBase {
	
	@FindBy(id = "productTitle")WebElement serachedProductTitle;
	@FindBy(xpath = "//div[@id='corePriceDisplay_desktop_feature_div']//span[@class='a-price-whole'] | //span[@class='a-price-whole']")WebElement serachedProductPrice;
	@FindBy(xpath = "(//div[@id='rightCol']//input[@id='add-to-cart-button'])[2] | //input[@id='add-to-cart-button']")WebElement addToCartButton;
	@FindBy(xpath="//a[contains(@href,'/cart') and contains(text(),'Go to Cart')]") WebElement proceedToCheckoutButton;
	
	WebElement serachProductPanel;
	// Create WebDriverWait instance
    WebDriverWait wait = new WebDriverWait(getdriver(), Duration.ofSeconds(20));

	public AmazonProductDetailsPage(){
		PageFactory.initElements(getdriver(), this);
	}
	 
	public String getSearchedProductTitle() {
		wait.until(ExpectedConditions.visibilityOf(serachedProductTitle));
		return serachedProductTitle.getText();
	} 

	public String getSearchedProductPrice() {
		wait.until(ExpectedConditions.visibilityOf(serachedProductPrice));
		return serachedProductPrice.getText();
	} 

	public double getProductPriceAsDouble() {
		String priceStr = getSearchedProductPrice();
		String cleanedStr = priceStr.replaceAll("[^0-9.]", "").trim();
		if (cleanedStr.isEmpty()) {
			return 0.0;
		}
		return Double.parseDouble(cleanedStr);
	}
	
	public void clickOnAddToCart() {
        addToCartButton.click();
	}
	
	public void clickProceedToCheckOut() {
        proceedToCheckoutButton.click();
	}
}
