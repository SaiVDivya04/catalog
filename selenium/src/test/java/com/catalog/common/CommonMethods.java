package com.catalog.common;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

import com.gargoylesoftware.htmlunit.javascript.host.Set;

public class CommonMethods {
	private WebDriver driver;
  	public  Actions builder;
  	public  ReadPropertyFile ReadPropertyFile;
  	public  WebDriverWait wait;
	public  String OSName=System.getProperty("os.name");
	public  boolean bExeResult=false;
	public  Logger logger= Logger.getLogger(CommonMethods.class);	
	public  static String rootWindow = null;
	private static int pass = 0;
	private static int fail = 0;
	private static String startTime;
	private static String endTime;

	public WebDriver openBrowser(String remoteBrowserType) throws Exception
	{		
		ReadPropertyFile =new ReadPropertyFile();
		DesiredCapabilities Capabilities = new DesiredCapabilities();
		String browser =ReadPropertyFile.getConfigPropertyVal("BrowserType");
		if(OSName.toLowerCase().contains("windows")){
			File IEfile = new File("C:\\Selenium\\Batch0317\\Catalog\\drivers\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", IEfile.getAbsolutePath());		
			File chromedriver = new File("C:\\Selenium\\drivers\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
			
			logger.info("Browser = " + browser);
			
			if (browser.equalsIgnoreCase("FireFox")) {
			System.setProperty("webdriver.gecko.driver", "/Users/Divya/Documents/SeleniumClass/Drivers/geckodriver");
	       	 driver = new FirefoxDriver();
	       	// driver.manage().deleteAllCookies();
			}
			else if (browser.equalsIgnoreCase("Safari")) {
				 //Assert.assertTrue(isSupportedPlatform());
		       	 driver = new SafariDriver();
		       	 //driver.manage().deleteAllCookies();
				}
			else if(browser.equalsIgnoreCase("IE")) {	
	      	    Capabilities = DesiredCapabilities.internetExplorer();
	      	    Capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	      	    driver = new InternetExplorerDriver(Capabilities);      	    
			} 
			else if(browser.equalsIgnoreCase("Chrome")) {
	       		Capabilities = DesiredCapabilities.chrome();
	       		Capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
	       		driver = new ChromeDriver(Capabilities);       	
			}
			else if(browser.equalsIgnoreCase("Remote")) {
				logger.info("Browser is=" + remoteBrowserType );
				
				if (remoteBrowserType.equalsIgnoreCase("FireFox"))
					driver= new RemoteWebDriver(DesiredCapabilities.firefox());			
	          	else if(remoteBrowserType.equalsIgnoreCase("IE")){
	          		Capabilities = DesiredCapabilities.internetExplorer();
	          		Capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	          		driver= new RemoteWebDriver(Capabilities);
	          	}
	         	else if(remoteBrowserType.equalsIgnoreCase("Chrome")) 
	      	    	driver= new RemoteWebDriver(DesiredCapabilities.chrome());
				//driver = new Augmenter().augment(driver);  
				else if(remoteBrowserType.equalsIgnoreCase("Safari")) 
					//Assert.assertTrue(isSupportedPlatform());
	      	    	driver= new RemoteWebDriver(DesiredCapabilities.safari());
				driver = new Augmenter().augment(driver);  
			}
		}else if(OSName.toLowerCase().contains("mac")){
			if(browser.equalsIgnoreCase("Remote")){ 
				//Capabilities = DesiredCapabilities.safari();
				driver = new RemoteWebDriver(DesiredCapabilities.safari());
			}else if(remoteBrowserType.equalsIgnoreCase("Chrome")) {
//				Capabilities = DesiredCapabilities.safari();
//				driver = new SafariDriver(Capabilities);
				logger.info("Chrome Browser Opening");
				System.setProperty("webdriver.chrome.driver", "/Users/Divya/Documents/SeleniumClass/Drivers/chromedriver");
				driver = new ChromeDriver();
				//driver=new ChromeDriver();
			}else if(remoteBrowserType.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", "/Users/Divya/Documents/SeleniumClass/Drivers/geckodriver");
				driver = new FirefoxDriver();
				logger.info("OPening the firefox browser");
			}else{
				logger.info("No browser is selected="+remoteBrowserType);
			}
				
		}
        
		driver.manage().deleteAllCookies();    
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
        driver.manage().window().maximize();   
        builder=new Actions(driver);
      //  selenium = new WebDriverBackedSelenium(driver, ReadPropertyFile.getConfigPropertyVal("URL"));
        logger.info("Executed Before Class Method Successfully");
        
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		logger.info("version: " + caps.getVersion());
		String browserName=caps.getBrowserName();
        String browserVersion=caps.getVersion();
        //String OStype=Capabilities.toString();
        logger.info("\n"+"Browser="+browserName+"\n"+"BrowserVersion="+browserVersion+"\n"+"OS="+System.getProperty("os.name")+"\n"+"OSVersion="+System.getProperty("os.version"));
//       driverScreenshot = new Augmenter().augment(driver);
        return driver;
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public void setValue(WebElement slocator,String sValue){
		String Element=slocator.getText();
		try {	
			logger.info(Element + "trying to set the value");
			slocator.clear();
			slocator.sendKeys(sValue);
			logger.info(sValue + " entered");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(Element + "field not found");
		}
	}
	
	public void click(WebElement slocator){
		try {
			
			String Element=slocator.getText();
			if ((Element.isEmpty()) || (Element==null)){
				Element=slocator.getAttribute("value");
			}
			logger.info(Element + " trying to click");

			slocator.click();
			logger.info(Element + " clicked ");
			
			acceptPopup();
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(3000);		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(slocator + " not clicked ");
		}
	}
	
	public void acceptPopup() {
		try {
			Alert alert = driver.switchTo().alert();
			//Thread.sleep(10000);
			alert.accept();
			driver.switchTo().defaultContent();
			logger.info("Alert Accepted");
		} catch (Exception e) {
			// Sometimes the text exist, but not the accept button.
			logger.info("Alert not found");
			//		
		}
	
		}

	public  void tearDown() {
	try{
		logger.info("Closing browser...");
		driver.quit();
		logger.info("Browser closed.");
	}
	catch(WebDriverException e){
		logger.info("Browser is already closed.");
	}
	rootWindow = null;
	
	
	}
	
	 public void verifyText(String expected){
		 try{
			 driver.findElement(By.xpath("//*[contains(text(),'"+ expected.trim() +"')]"));
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" verified");
			// return true;
		 }
		 catch(Exception e){
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
			 Assert.fail("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
		 }
		 
	 }
	 
	 
	 public void verifyHeader(String expected){
		 try{
			 
			 driver.findElement(By.xpath("//*[contains(text(),'"+ expected.trim() +"')]"));
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" verified");
			// return true;
		 }
		 catch(NoSuchElementException e){
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
			 Assert.fail("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
		 }
		 
	 }
	
	public void navigateToSite(String remoteBrowserType) throws Exception{
		try {
			navigateURL();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			logger.info("URL Navigation");
	     }catch(Exception e){
		   logger.info("No browser found. NewBrowser Opening");
/*		   if(driver != null) {
			   driver.quit();
	        }*/		
		   openBrowser(remoteBrowserType);
		   navigateURL();
		   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		   logger.info("URL Navigation");
	        }
	}
	
	public void getPage(String URL){
		if(rootWindow != null){
			driver.switchTo().window(rootWindow);
			driver.get(URL);
			logger.info("URL navigating to ="+URL);
		}
		else{
			driver.get(URL);
			logger.info("URL navigating to ="+URL);
			rootWindow = driver.getWindowHandle();
			//windowHandles = driver.getWindowHandles();
		}
	}
	public void navigateURL(){
		getPage(getURL("URL"));	
	}
	
	public void navigateURL(String ConfigFileURL){
		String getURL=getURL(ConfigFileURL);
		getPage(getURL);	
	}
	
	
	public void navigateURL(String remoteBrowserType,String ConfigFileURL) throws Exception{
		String getURLfromConfig=getURL(ConfigFileURL);	
		logger.info("URL is="+getURLfromConfig);
		try {
			getPage(getURLfromConfig);	
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			logger.info("URL Navigation");
	     }catch(Exception e){
		   logger.info("No browser found. NewBrowser Opening");
/*		   if(driver != null) {
			   driver.quit();
	        }*/		
		   openBrowser(remoteBrowserType);
		   getPage(getURLfromConfig);	
		   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		   logger.info("URL Navigation");
	        }
	}
	
	@SuppressWarnings("static-access")
	public String getURL(String ConfigURL){
		//if (appUrl == null) {
		String appUrl=null;
			try {
		       appUrl =ReadPropertyFile.getConfigPropertyVal(ConfigURL); //ReadPropertyFile.ReadFile(PropertyFilePath.ConfigPathLocation, URL);
		       logger.info("URL = " + appUrl);
		       //driver.get(appUrl);
			}catch (Exception ex) {
				appUrl = "www.google.com";
				logger.info("URL not found in COnfig.properties file. So opening default site = " + appUrl);
			}
			//assert appUrl.contains("http");
	//	}
		return appUrl; 
	}
	
	
	


	
	public void closeBrowser()
	{
		driver.quit();
	}
	
	
	@SuppressWarnings("static-access")
	 public void postResults( ITestResult it) throws SQLException{
			logger.info("Test description: " + it.getMethod().getDescription());
			logger.info("getMethod name:" +it.getMethod());
			logger.info("getName name:" +it.getName()); //tcID
			logger.info("getTestClass name:" +it.getTestClass()); //null
			logger.info("getThrow name:" +it.getThrowable());
			String TestCaseID = it.getName(); 
			String HostID=System.getenv().get("COMPUTERNAME");
			logger.info(HostID);
			ReadPropertyFile =new ReadPropertyFile();
			String className = it.getTestClass().toString().replace("[TestClass name=class", "");
			className = className.replace("]", "");
			
			if((pass+fail) == 0){
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				Date date = new Date();
				startTime = dateFormat.format(date);	
				logger.info("Start time: " + startTime);
			}
			
			if (it.isSuccess()){
				pass++;
				try {	
					logger.info("Pass");

					}catch(Exception ex){
						System.out.print(ex.getMessage());
					}
						
			}else{
				fail++;
				try
				{
					if(it.getStatus() == ITestResult.SKIP){
						logger.info("Skipped");
					
					}
					else{
						logger.info("Fail");
						ScreenCapture screenCapture=new ScreenCapture(driver);
						String imgPath = screenCapture.takeScreenShoot(it.getMethod());
					
										
						logger.info("screenshot captured for: " +it.getMethod()+ " Failed TestCase");
					}
								
					
					//closeBrowser();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}


				
		}	


    	
	public void selectDropdown(WebElement slocator, String value){
		List<WebElement> getDropDownValues=slocator.findElements(By.tagName("option"));
		boolean match = false;
		logger.info("Total no. of dropdown values:"+ getDropDownValues.size());
		for(int i = 0; i< getDropDownValues.size();i++){
			logger.info(getDropDownValues.get(i).getText());
			 if (getDropDownValues.get(i).getText().equalsIgnoreCase(value)){
				 getDropDownValues.get(i).click();
				 match = true;
			     break;}

		}
		if(!match){
			logger.info("No Selection Found");
			Assert.fail(value + "Not found in the dropdown " + slocator);
			 
		}
	  }

	 public void navigateBrowserback(){
		 driver.navigate().back();
	 }
	 
	 public void navigateBrowserFW(){
		 driver.navigate().forward();
	 }
	 
	
	
	
	
}
