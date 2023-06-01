package com.qa.opencart.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

class RegisterPageTest extends BaseTest{

	
	
	@BeforeClass
	public void regSetUp() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmailId() {
		return "testautomation"+System.currentTimeMillis()+"@gmail.com";
		//return "testautomation" + UUID.randomUUID()+ "@gmail.com";//will generate random numbers
	}
	
//	@DataProvider(name = "regData")
//	public Object[][] getUserRegTestData() {
//		return new Object[][] {
//			{"Jeff", "arun", "1276543218", "arun@12", "yes"},
//			{"robinson", "martinez", "1276543200", "robin@12", "no"},
//			{"amber", "automation", "9856543218", "amber@12", "yes"}
//			
//		};
//	}
	
	@DataProvider(name= "regExcelData")
	public Object[][] getRegExcelTestData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	
	
	
	
	
	
	
	
	
	@Test(dataProvider= "regExcelData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		String actRegSuccMessg = registerPage.registerUser(firstName, lastName, getRandomEmailId(), telephone, password, subscribe);
		System.out.println(actRegSuccMessg);
		//Assert.assertEquals(actRegSuccMessg, AppConstants.USER_REG_SUCCESS_MESSG);
	}
	
}
