package com.ama.qa.pages;

import java.time.Duration;
import java.util.List;

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
public class ShoppingCartPage extends TestBase {

	@FindBy(xpath = "//input[@name='proceedToRetailCheckout']")
	WebElement proceedToBuy;

	@FindBy(xpath = "//span[@id='sc-subtotal-amount-activecart']//span[contains(@class,'sc-price')] | //span[@id='sc-subtotal-amount-buybox']//span[contains(@class,'sc-price')] | //span[contains(@class,'sc-price')]")
	WebElement subtotalPrice;

	@FindBy(xpath = "//div[contains(@class,'sc-list-item-content')]")
	List<WebElement> cartItems;

	WebDriverWait wait = new WebDriverWait(getdriver(), Duration.ofSeconds(15));

	public ShoppingCartPage() {
		PageFactory.initElements(getdriver(), this);
	}

	public void clickProceedToBuy() {
		wait.until(ExpectedConditions.elementToBeClickable(proceedToBuy)).click();
	}

	/**
	 * Retrieves the subtotal price from the cart and converts it to a double.
	 * Removes currency symbols (e.g. ₹), commas, and spaces.
	 *
	 * @return Double value of the total price
	 */
	public double getCartSubtotalPrice() {
		try {
			wait.until(ExpectedConditions.visibilityOf(subtotalPrice));
			String rawPrice = subtotalPrice.getText();
			return parsePrice(rawPrice);
		} catch (Exception e) {
			List<WebElement> priceElements = getdriver().findElements(By.xpath("//span[contains(@id,'sc-subtotal-amount')]//span | //span[contains(@class,'sc-price')]"));
			for (WebElement el : priceElements) {
				String text = el.getText().trim();
				if (!text.isEmpty()) {
					return parsePrice(text);
				}
			}
			throw new RuntimeException("Could not extract shopping cart subtotal price.", e);
		}
	}

	/**
	 * Parses price string into numeric double value.
	 */
	private double parsePrice(String priceText) {
		String cleanedText = priceText.replaceAll("[^0-9.]", "").trim();
		if (cleanedText.isEmpty()) {
			return 0.0;
		}
		return Double.parseDouble(cleanedText);
	}

	/**
	 * Checks if total price in cart is greater than specified minimum amount.
	 */
	public boolean isTotalPriceGreaterThan(double minPrice) {
		return getCartSubtotalPrice() > minPrice;
	}

	/**
	 * Gets total items count in cart.
	 */
	public int getCartItemsCount() {
		return cartItems.size();
	}
}
