package com.ama.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;
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
public class AmazonLoginPage extends TestBase{
	@FindBy(xpath = "//span[text()='Log In']")WebElement logInButton;
	@FindBy(name = "email")WebElement email;
	@FindBy(id = "continue")WebElement continueButton;
	@FindBy(id = "ap_password")@CacheLookup WebElement password;
	@FindBy(id = "signInSubmit")WebElement signInSubmit;
	
	WebDriverWait wait = new WebDriverWait(getdriver(), Duration.ofSeconds(15));
	public AmazonLoginPage(){
		PageFactory.initElements(getdriver(), this);
	}
	
	public String gettitle(){
		return getdriver().getTitle();
	}

	
	public void enterEmailOrPhone(String emailOrPhone){
		wait.until(ExpectedConditions.visibilityOf(email));
		email.sendKeys(emailOrPhone);
	}
	
	public void clickContinue(){
		continueButton.click();
	}
	public void enterPassword(String passwordValue){
		wait.until(ExpectedConditions.visibilityOf(password));
		password.sendKeys(passwordValue);
	}
	
	
	public void clickSignIn(){
		signInSubmit.click();
	}
}
