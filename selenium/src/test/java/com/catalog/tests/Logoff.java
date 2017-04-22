package com.catalog.tests;


	import java.io.FileInputStream;
	import java.io.InputStream;

	import org.apache.poi.sl.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.WebDriver;
	import org.testng.ITestResult;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Parameters;
	import org.testng.annotations.Test;

	import com.catalog.common.CommonMethods;
	import com.catalog.common.DataDriverXLS;
	import com.catalog.pages.SignInPage;
	import com.catalog.pages.WelcomePage;

	public class Logoff {
		private WebDriver driver;
		public CommonMethods CM;
		
		
		@Parameters({ "remoteBrowserType"})
		@BeforeClass(alwaysRun=true)
		public void setup(String remoteBrowserType) throws Exception {
			CM = new CommonMethods();
			driver = CM.openBrowser(remoteBrowserType);
		
		}
		
		@Parameters({ "remoteBrowserType"})
		@BeforeMethod(alwaysRun=true)
		public void navigate(String remoteBrowserType) throws Exception {
			CM.navigateToSite(remoteBrowserType);
			driver = CM.getDriver();
		
		}
		
		@AfterMethod(alwaysRun=true) 
		public void after(ITestResult it) throws Exception{
			//it.setThrowable(new Throwable("Error capture: blah blah. \n" + "System error: " + it.getThrowable().getMessage()));
			//CF.postResults(it);
		 //   driver.manage().deleteAllCookies(); 
			CM.tearDown();
		}	
		

		@Test
		public void testLogin01(){
			//System.out.println("I am in Test");
			WelcomePage WelcomePage = new WelcomePage(driver);
		
			SignInPage SignInPage=WelcomePage.clickLogYOurSelfLink();
		
			//DataDriverXLS DD=new DataDriverXLS("/drivers/Testdata.xlsx");
			//String getUserName=DD.getCellData("Sheet1", 1, 1);
			//System.out.println("getUserName="+getUserName);
			SignInPage.validLogin();
			SignInPage.clickLogOff();


		}
		

	}


