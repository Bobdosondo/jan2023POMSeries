package com.qa.opencart.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	
	@BeforeClass
	public void setUpAccPage() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	
	
	
	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAcctPageTitle();
		Assert.assertEquals(actTitle,AppConstants.ACCOUNTS_PAGE_TITLE_VALUE );
		
	}
	
	@Test
	public void isLogoutLinkExist() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
		
		
	}
	
	@Test
	public void isMyAccountLinkExist() {
		Assert.assertTrue(accPage.isMyAccountLinkExist());
		
		
	}
	
	@Test
	public void accPageHeadersCountTest() {
		List<String> actHeadersList = accPage.getAccountHeadersList();
		Assert.assertEquals(actHeadersList.size(), 4);
		
		
	}
	@Test
	public void accPageHeadersTest() {
		List<String> actHeadersList = accPage.getAccountHeadersList();
		
		//Collections.sort(expectedHeadersList);
		Assert.assertEquals(actHeadersList,AppConstants.EX_ACCOUNTS_HEADERS_LIST );
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
