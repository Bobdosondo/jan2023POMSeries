package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1.constructor of the Account page class
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	// 2.by loactors:
	private By logout = By.linkText("Logout");
	private By myAccount = By.linkText("My Account");
	By accHeaders = By.xpath("//div[@id='content']/h2");
	By seacrch = By.name("search");
	By searchIcon = By.cssSelector("div#search button");

	// 3.page actions:
	public String getAcctPageTitle() {
		return eleUtil.waitForTitleIsAndCapture(AppConstants.ACCOUNTS_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
//		String title = driver.getTitle();
//		System.out.println("Acc page title :" + title);
//		return title;

	}

	public boolean isLogoutLinkExist() {
		return eleUtil.checkElementIsDisplayed(logout);

		// return driver.findElement(logout).isDisplayed();
	}

	public boolean isMyAccountLinkExist() {
		return eleUtil.checkElementIsDisplayed(myAccount);
		// eturn driver.findElement(myAccount).isDisplayed();
	}

	public List<String> getAccountHeadersList() {
		List<WebElement> headersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> headrsValList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headrsValList.add(text);

		}
		return headrsValList;
	}

	public ResultsPage doSearch(String producName) {
		eleUtil.waitForElementVisible(seacrch, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendkeys(seacrch, producName);
		
		eleUtil.doClick(searchIcon);
		
		

//		driver.findElement(seacrch).sendKeys(producName);
//		driver.findElement(searchIcon).click();
		return new ResultsPage(driver);
	}

	
}
