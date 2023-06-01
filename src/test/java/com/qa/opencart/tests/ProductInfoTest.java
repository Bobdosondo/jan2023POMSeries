package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest{
	
	
	
	@BeforeClass
	public void AccPageSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test
	public void productInfoTest() {
		resultsPage = accPage.doSearch("Macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String , String> productInfoMap = productInfoPage.getProductInfo();
		System.out.println(productInfoMap);
		
		//HashMap:
		//{Brand=Apple, Availability=In Stock, extaxprice=$2,000.00, Product Code=Product 18,
		//productname=MacBook Pro, Reward Points=800, productprice=$2,000.00}
		
		//LinkedHashMap:maintaining order
		//{Brand=Apple, Product Code=Product 18, Reward Points=800, Availability=In Stock,
		//productprice=$2,000.00, extaxprice=$2,000.00, productname=MacBook Pro}
		
		//TreeMap:sorted order in a bases of capital letter
		//{Availability=In Stock, Brand=Apple, Product Code=Product 18, Reward Points=800, extaxprice=$2,000.00,
		//productname=MacBook Pro, productprice=$2,000.00}
		
		
//		Assert.assertEquals(productInfoMap.get("Brand"), "Apple");
//		Assert.assertEquals(productInfoMap.get("Availability"), "In Stock");
//		Assert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
//		Assert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
//		Assert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		//hard Asserts are static in nature but SoftAssert non static 
		
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertAll();
		//mandatory to write the last line so it will run all the tests cases and tell you
		//the failed and the passes tests
		//for the hard asserts when one test case gets failed it will not run the following test cases
		//if the last line is not written it will not show you the test cases
		//when we have multiple features to check ,it recommended to use softassertions
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
