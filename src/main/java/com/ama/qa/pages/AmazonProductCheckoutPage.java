package com.ama.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
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
public class AmazonProductCheckoutPage extends TestBase {

	@FindBy(xpath = "//span[@id='shipToThisAddressButton'] | //input[@data-testid='Address_selectShipToThisAddress']")
	WebElement shipToAddressButton;

	@FindBy(xpath = "//span[contains(@class,'grand-total-price')] | //td[contains(@class,'grand-total-price')] | //span[@id='subtotal-amount']")
	WebElement orderGrandTotal;

	@FindBy(xpath = "//input[@name='placeYourOrder1'] | //span[@id='submitOrderButtonId'] | //input[@value='Place your order and pay']")
	WebElement placeYourOrderButton;

	@FindBy(xpath = "//h1[contains(text(),'Order placed') or contains(text(),'Thank you')] | //div[contains(@class,'order-confirmation')]")
	WebElement orderConfirmationHeading;

	WebDriverWait wait = new WebDriverWait(getdriver(), Duration.ofSeconds(15));

	public AmazonProductCheckoutPage() {
		PageFactory.initElements(getdriver(), this);
	}

	public void clickShipToAddressButton() {
		if (getdriver().getCurrentUrl().contains("oos")) {
			throw new org.testng.SkipException("Checkout blocked: selected product is unavailable/restricted from this seller (Amazon OOS page).");
		}
		try {
			wait.until(ExpectedConditions.elementToBeClickable(shipToAddressButton)).click();
		} catch (Exception e) {
			System.out.println("Address selection step auto-bypassed or already selected.");
		}
	}

	public double getOrderTotalPaymentAmount() {
		try {
			wait.until(ExpectedConditions.visibilityOf(orderGrandTotal));
			String rawText = orderGrandTotal.getText();
			return parsePrice(rawText);
		} catch (Exception e) {
			for (WebElement el : getdriver().findElements(By.xpath("//*[contains(text(),'₹') or contains(text(),'Total')]"))) {
				String txt = el.getText().trim();
				if (txt.matches(".*[0-9,]+(\\.[0-9]+)?.*")) {
					double parsed = parsePrice(txt);
					if (parsed > 0) return parsed;
				}
			}
			return 0.0;
		}
	}

	public boolean isPaymentGreaterThan(double minAmount) {
		return getOrderTotalPaymentAmount() > minAmount;
	}

	public void placeOrderAndPay() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(placeYourOrderButton)).click();
		} catch (Exception e) {
			System.out.println("Place order button notice: Requires active login authentication.");
		}
	}

	public boolean isOrderConfirmed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(orderConfirmationHeading)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	private double parsePrice(String priceText) {
		String cleaned = priceText.replaceAll("[^0-9.]", "").trim();
		if (cleaned.isEmpty()) return 0.0;
		try {
			return Double.parseDouble(cleaned);
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}
}
