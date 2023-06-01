package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By productHeader = By.cssSelector("div#content h1");
	private By ProductImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCart = By.id("button-cart");
	
	private Map<String , String> productInfoMap;
	
	
	
	
	
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
		
	}
	
	public String getProductHeaderName() {
		return eleUtil.doGetElementText(productHeader);
		
	}
	
	public int getProdcutImagesCount() {
		
		return eleUtil.waitForElementsVisible(ProductImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
	}
	
	public Map<String, String> getProductInfo() {
		//productInfoMap = new HashMap<String, String>();
		//productInfoMap = new LinkedHashMap<String, String>();
		productInfoMap = new TreeMap<String, String>();
		
		
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productname", getProductHeaderName());
		return productInfoMap;
		
	}
	 
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock

	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e : metaList) {
			String metatText =e.getText();
			String metaInfo[] = metatText.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
			
			
		}
		
	}
//	$2,000.00
//	Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String priceValue = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText();
		String exTaxPricevValue = exTaxPrice.split(":")[1].trim();//$2,000.00
		productInfoMap.put("productprice", priceValue);//you can add your own custom key;
		productInfoMap.put("extaxprice", priceValue);
		
	}
	

}
