package com.catalog.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.catalog.common.CommonMethods;


public class LogOffPage {
        private WebDriver driver;
		public CommonMethods CM;
		
		@FindBy(how = How.XPATH, using = "//*[@id='bodyContent']/div/div[1]/a[1]/u")
		private WebElement LOGYOURSELFIN;
		
		public LogOffPage(WebDriver driver){
			this.driver = driver;
			PageFactory.initElements(driver,this);
			CM=new CommonMethods();
			//CM.verifyText("Welcome to iBusiness");
			
		}
		
		public SignInPage clickLogYOurSelfLink(){
			CM.click(LOGYOURSELFIN);
			
			return new SignInPage(driver);
			
		}
		

	}



