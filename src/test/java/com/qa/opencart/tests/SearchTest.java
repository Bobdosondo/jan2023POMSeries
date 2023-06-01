package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;

public class SearchTest extends BaseTest{
	
	
	@BeforeClass
	public void searchSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	
	
	
	@Test(dataProvider="productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)//TDD=data driven testing approach
	public void searchProductResultCountTest(String searchKey) {
		resultsPage = accPage.doSearch(searchKey);
		Assert.assertTrue(resultsPage.getProductResultsCount()>0);
	}
	
	
	
	@Test(dataProvider="productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
	public void searchPageTitleTest(String searchKey) {
		resultsPage = accPage.doSearch(searchKey);
		String actSearchTitle =resultsPage.getResultsPageTitle(searchKey);
		System.out.println("search Page Title : " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - "+ searchKey);
		
		
		
	}
	
	@Test(dataProvider = "productDataWithName", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(String searkKey, String productName) {
		resultsPage = accPage.doSearch(searkKey);
		productInfoPage = resultsPage.selectProduct(productName);
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("actual product name : " + actProductHeaderName);
		Assert.assertEquals(actProductHeaderName,productName );
		
	}
	
	
	
	@Test(dataProvider= "productDataWithImage", dataProviderClass = ProductDataProvider.class)
	public void productImagesTest(String searchKey,String productName, int expImagesCount) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		int actProductImagesCount = productInfoPage.getProdcutImagesCount();
		System.out.println("actual product images count :" + actProductImagesCount );
		Assert.assertEquals(actProductImagesCount, expImagesCount);
		
	}
	

}
