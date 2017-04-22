package com.catalog.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.catalog.common.CommonMethods;

public class SignInPage {
	
	private WebDriver driver;
	@FindBy(how = How.NAME, using = "email_address")
    private WebElement EMAILADDRESS;
    
    @FindBy(how = How.NAME, using = "password")
    private WebElement PASSWORD;
    
    @FindBy(how = How.XPATH, using = "//*[@id='tdb5']/span[1]")
    private WebElement SIGNIN;
    
    @FindBy(how = How.XPATH, using = "//*[@id='bodyContent']/div/div[1]/a[1]/u")
    private WebElement LOGYOURSELFIN;
    
    @FindBy(how = How.XPATH, using = "//*[@id='tdb4']/span")
    private WebElement LOGOFF;
    CommonMethods CM;
	
	public SignInPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver,this);
		CM=new CommonMethods();
	//	CM.verifyText("Welcome, Please Sign In");
	}
	
	public void enterUserName(String username){
		//CM=new CommonMethods();
		CM.setValue(EMAILADDRESS, username);	
	}
	
	public void enterPassword(String password){
		//CM=new CommonMethods();
		CM.setValue(PASSWORD, password);	
	}
	
	public void clickSignInButton(){	   
		CM.click(SIGNIN);
		//System.out.println("Sign In Clicked");
	}
	
	public WelcomePage validLogin(){
		enterUserName("ecalix@test.com");
		enterPassword("test123");
		clickSignInButton();
		return new WelcomePage(driver);
	}
	

	public void clickLogOff(){
		//  LOGOFF.click();
		CM.click(LOGOFF);
	}

}
