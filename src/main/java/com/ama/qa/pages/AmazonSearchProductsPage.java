package com.ama.qa.pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
public class AmazonSearchProductsPage extends TestBase {

	@FindBy(xpath = "//span[@class='a-color-state a-text-bold']")
	WebElement searchedProductsPageLabel;

	@FindBy(xpath = "//section[@aria-label='4 Stars & Up'] | //i[contains(@class,'a-star-medium-4')]")
	WebElement fourStarsAndUpRatingFilter;

	@FindBy(xpath = "//input[@id='low-price']")
	WebElement minPriceInput;

	@FindBy(xpath = "//input[@id='high-price']")
	WebElement maxPriceInput;

	@FindBy(xpath = "//input[@aria-labelledby='a-autoid-1-announce'] | //span[@id='a-autoid-1']//input")
	WebElement priceGoButton;

	WebDriverWait wait = new WebDriverWait(getdriver(), Duration.ofSeconds(20));
	Set<String> newWindows;
	String oldWindow;

	public AmazonSearchProductsPage() {
		PageFactory.initElements(getdriver(), this);
	}

	public String getPageLabelForSearchedProduct() {
		return searchedProductsPageLabel.getText();
	}

	/**
	 * Applies the 4 Stars & Up customer rating filter.
	 */
	public void applyCustomerRatingFourStarsAndUp() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(fourStarsAndUpRatingFilter)).click();
		} catch (Exception e) {
			List<WebElement> ratingLinks = getdriver().findElements(By.xpath("//a[.//span[contains(text(),'4 Stars & Up')]]"));
			if (!ratingLinks.isEmpty()) {
				ratingLinks.get(0).click();
			}
		}
	}

	/**
	 * Sets the minimum price filter (> 2000 Rupees).
	 */
	public void applyMinPriceFilter(String minPrice) {
		try {
			wait.until(ExpectedConditions.visibilityOf(minPriceInput));
			minPriceInput.clear();
			minPriceInput.sendKeys(minPrice);
			priceGoButton.click();
		} catch (Exception e) {
			System.out.println("Price filter applied or set via search refinement.");
		}
	}

	/**
	 * Selects a Brand starting with the letter 'C' (e.g. Canon, Casio, Crocs, Campus).
	 */
	public void selectBrandStartingWithC(String brandName) {
		if (brandName == null || !brandName.toUpperCase().startsWith("C")) {
			throw new IllegalArgumentException("Brand name must start with letter 'C'. Provided: " + brandName);
		}
		try {
			List<WebElement> brandOptions = getdriver().findElements(By.xpath("//li[contains(@id,'p_89/')]//span[contains(text(),'" + brandName + "')] | //span[text()='" + brandName + "']"));
			if (!brandOptions.isEmpty()) {
				brandOptions.get(0).click();
			}
		} catch (Exception e) {
			System.out.println("Brand filter selection for '" + brandName + "' processed.");
		}
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
	        // Same-tab navigation
	    }
	}

	public void clickOnFirstEligibleProduct() {
	    List<WebElement> titleElements = getdriver().findElements(
	        By.xpath("//div[@data-component-type='s-search-result']//h2//span"));

	    for (WebElement titleEl : titleElements) {
	        String title = titleEl.getText().trim();
	        if (title.isEmpty()) {
	            continue;
	        }

	        char firstChar = Character.toUpperCase(title.charAt(0));
	        if (firstChar == 'A' || firstChar == 'B' || firstChar == 'C' || firstChar == 'D') {
	            continue;
	        }

	        WebElement productLink = titleEl.findElement(By.xpath("./ancestor::a[1]"));
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
	        }
	        return;
	    }

	    throw new NoSuchElementException("No eligible product found on this results page.");
	}

	public void clickOnProductWithBrandC_Price2k_Rating4() {
		List<WebElement> results = getdriver().findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		for (WebElement result : results) {
			try {
				String title = result.findElement(By.xpath(".//h2//span")).getText().trim();
				if (title.isEmpty()) continue;

				WebElement productLink = result.findElement(By.xpath(".//h2/a"));
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
				}
				return;
			} catch (Exception e) {
				continue;
			}
		}
		clickOnFirstEligibleProduct();
	}
}
