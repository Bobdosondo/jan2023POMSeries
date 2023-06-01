package com.qa.opencart.utils;

	
	import java.time.Duration;
	import java.util.ArrayList;
	import java.util.List;

	import org.openqa.selenium.Alert;
	import org.openqa.selenium.By;
	import org.openqa.selenium.NoSuchElementException;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.frameworkexception.FrameException;

	public class ElementUtil {
		private WebDriver driver;
		private JavaScriptUtil jsUtil;
		private final int DEFAULT_TIME_OUT = 5;

		public ElementUtil(WebDriver driver) {
			this.driver = driver;
			jsUtil = new JavaScriptUtil(this.driver);

		}

		public void doSendkeys(By locator, String value) {
			if (value == null) {
				System.out.println("null values are not allowed");
				throw new FrameException("VALUECANNOTBENULL");
			}
			WebElement ele = getElement(locator);
			ele.clear();
			ele.sendKeys(value);
			
			
			
			
		}
		

		public void doClick(By locator) {
			getElement(locator).click();

		}

		public void doClick(By locator, int timeOut) {
			checkElementClickable(locator, timeOut).click();
		}

		public WebElement getElement(By locator, int timeOut) {
			WebElement element = waitForElementVisible(locator, timeOut);
			if(Boolean.parseBoolean(DriverFactory.highlightElement) ){
				jsUtil.flash(element);
			}
			return element;

		}

		public WebElement getElement(By locator) {
			WebElement element = null;

			try {

				element = driver.findElement(locator);
				System.out.println("element is found with locator " + locator);

			} catch (NoSuchElementException e) {

				System.out.println("Element is not found using this locator.." + locator);

				element = waitForElementVisible(locator, DEFAULT_TIME_OUT);

			}
			
			
			if(Boolean.parseBoolean(DriverFactory.highlightElement) ){
				jsUtil.flash(element);
			}
			
			return element;

		}

		public void doClear(By locator) {
			getElement(locator).clear();
		}

		public String doGetElementText(By locator) {
			return getElement(locator).getText();
		}

		public boolean checkElementIsDisplayed(By locator) {
			return getElement(locator).isDisplayed();
		}

		public String doGetAttributeValue(By locator, String attName) {
			return getElement(locator).getAttribute(attName);
		}

		public int getElementsCount(By locator) {
			return getElements(locator).size();
		}

		public boolean checkIfElementIsDisplayed(By locator) {
			List<WebElement> eleList = getElements(locator);
			if (eleList.size() > 0) {
				System.out.println(locator + "element is present on the page");
				return true;
			} else {
				return false;
			}
		}

		public List<String> getElementsAttributeValue(By locator, String attName) {
			List<WebElement> eleList = getElements(locator);
			List<String> eleAttrList = new ArrayList<String>();// 0

			for (WebElement e : eleList) {
				String AttValue = e.getAttribute(attName);
				// System.out.println(AttValue);
				eleAttrList.add(AttValue);

			}
			return eleAttrList;// for the validation point of view;
		}

		public List<String> getElementsTextList(By locator) {

			List<WebElement> elementslinkList = getElements(locator);
			List<String> elementsTextList = new ArrayList<String>();

			for (WebElement e : elementslinkList) {
				String text = e.getText();
				elementsTextList.add(text);

			}
			return elementsTextList;
		}

		public List<WebElement> getElements(By locator) {
			return driver.findElements(locator);
		}

		public void clickElementFromPage(By loctor, String eleText) {
			List<WebElement> eleList = getElements(loctor);

			for (WebElement e : eleList) {
				String text = e.getText();
				System.out.println(text);
				if (text.equals(eleText)) {
					e.click();
					break;
				}

			}
		}

		public void search(String searchKey, By searchLocator, String suggName, By suggestions)
				throws InterruptedException {

			doSendkeys(searchLocator, searchKey);
			Thread.sleep(3000);

			List<WebElement> suggestionList = getElements(suggestions);
			System.out.println("Total suggestion: " + suggestionList.size());

			if (suggestionList.size() > 0) {

				for (WebElement e : suggestionList) {
					String text = e.getText();
					if (text.length() > 0) {
						System.out.println(text);
					} else {
						System.out.println("blank values -- no suggestions");
						break;
					}

					if (text.contains(suggName)) {
						e.click();
						break;
					}

				}

			}

			else {
				System.out.println("no suggestions found");

			}
		}

		// *****************************Drop Down Utils

		public void doSelectDropDownByIndex(By locator, int index) {

//			WebElement ele =getElement(locator);
//			Select select = new Select(ele);
//			select.selectByIndex(index);

			Select select = new Select(getElement(locator));
			select.selectByIndex(index);

		}

		public void doSelectDropDownByVisibleText(By locator, String text) {

			Select select = new Select(getElement(locator));
			select.selectByVisibleText(text);

		}

		public void doSelectDropDownByValueAttribute(By locator, String value) {

			Select select = new Select(getElement(locator));
			select.selectByValue(value);

		}

		public int getDropDownValueCount(By locator) {
			return getAllDropDownOptions(locator).size();

		}

		public List<String> getAllDropDownOptions(By locator) {
			Select select = new Select(getElement(locator));
			List<WebElement> optionsList = select.getOptions();
			List<String> optionsValueList = new ArrayList<String>();
			System.out.println("Total values : " + optionsList.size());

			// print all the countries

			for (WebElement e : optionsList) {
				String text = e.getText();
				System.out.println(text);
				optionsValueList.add(text);
			}
			return optionsValueList;

		}

		public boolean doSelectDropDownValue(By locator, String dropDownValue) {
			boolean flag = false;
			Select select = new Select(getElement(locator));
			List<WebElement> optionsList = select.getOptions();
			System.out.println("Total values : " + optionsList.size());

			for (WebElement e : optionsList) {
				String text = e.getText();
				System.out.println(text);
				if (text.equals(dropDownValue)) {
					flag = true;
					e.click();
					break;
				}
			}
			if (flag == false) {
				System.out.println(dropDownValue + "is not present in the drop down" + locator);
			}
			return flag;
		}

		public boolean doSelectValueFromDropDownWithoutSelect(By locator, String value) {
			boolean flag = false;

			List<WebElement> optionsList = getElements(locator);

			for (WebElement e : optionsList) {
				String text = e.getText();
				if (text.equals(value)) {
					flag = true;
					e.click();
					break;
				}
			}
			if (flag = false) {
				System.out.println(value + "is not present in the drop down" + locator);
			}
			return flag;

		}

	//*****************************Actions class utils***********************************************

		public void doActionsSendKeys(By locator, String value) {
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator), value).build().perform();
		}

		public void doActionsClick(By locator) {
			Actions act = new Actions(driver);
			act.click(getElement(locator)).build().perform();
		}

		/**
		 * An expectation for checking an element is visible and enabled such that you
		 * can click it.
		 * 
		 * @param locator
		 * @param timeOut
		 */
		public void doActionsClick(By locator, int timeOut) {
			Actions act = new Actions(driver);
			// act.click(getElement(locator)).build().perform();
			act.click(checkElementClickable(locator, timeOut)).build().perform();
		}

		public void doDragAndDop(By sourceLocator, By targetLocator) {
			Actions act = new Actions(driver);
			act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();
		}

		public void doContextClick(By locator) {
			Actions act = new Actions(driver);
			act.contextClick(getElement(locator)).build().perform();

		}

		public void doMoveToElement(By locator) {
			Actions act = new Actions(driver);
			act.moveToElement(getElement(locator)).build().perform();

		}

		public void handleTwoLevelsMenu(By parentMenu, By childMenu) throws InterruptedException {

			doMoveToElement(parentMenu);
			Thread.sleep(2000);
			doClick(childMenu);

		}

		public void handleTwoLevelsMenu(By parentMenu, String childMenuLinkText) throws InterruptedException {

			doMoveToElement(parentMenu);
			Thread.sleep(2000);
			doClick(By.linkText(childMenuLinkText));

		}

		public void multiLevelMenuChildMenuHandle(By parentMenuLocator, String level2LinkText, String level3LinkText,
				String level4LinkText) throws InterruptedException {

			Actions act = new Actions(driver);

			WebElement level1 = getElement(parentMenuLocator);
			act.moveToElement(level1).build().perform();
			Thread.sleep(3000);

			WebElement level2 = getElement(By.linkText(level2LinkText));
			act.moveToElement(level2).build().perform();
			Thread.sleep(3000);

			WebElement level3 = getElement(By.linkText(level3LinkText));
			act.moveToElement(level3).build().perform();
			Thread.sleep(3000);

			doClick(By.linkText(level4LinkText));

		}

	//*******************     wait utils      *****************************************************************************

	//JavaScriptAlerts
		public Alert waitForAlertJsPopUp(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.alertIsPresent());

		}

		public String alertJsGetText(int timeOut) {
			return waitForAlertJsPopUp(timeOut).getText();

		}

		public void alertJsAccept(int timeOut) {
			waitForAlertJsPopUp(timeOut).accept();

		}

		public void alertJsGetDismiss(int timeOut) {
			waitForAlertJsPopUp(timeOut).dismiss();

		}

		public void enterAlertValue(int timeOut, String value) {
			waitForAlertJsPopUp(timeOut).sendKeys(value);

		}

		public String waitForTitleIsAndCapture(String titleFraction, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				String title = driver.getTitle();

				return title;
			} else {
				System.out.println("titlt is not found within the given timeOut: " + timeOut);
				return null;
			}

		}

		public String waitForFullTitleAndCapture(String titleValue, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			if (wait.until(ExpectedConditions.titleIs(titleValue))) {
				String title = driver.getTitle();

				return title;
			} else {
				System.out.println("titlt is not found within the given timeOut: " + timeOut);
				return null;
			}

		}
		
		
		public String waitForURLContainsAndCapture(String URLFraction, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			 if(wait.until(ExpectedConditions.urlContains(URLFraction))) {
				String url =driver.getCurrentUrl();
				
				return url;
		}
			 else {
				 System.out.println("URL is not found within the given timeOut: " + timeOut);
				 return null;
			 }
			 
		}
		
		public String waitForURLAndCapture(String URLValue, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			 if(wait.until(ExpectedConditions.urlToBe(URLValue))) {
				String url =driver.getCurrentUrl();
				
				return url;
		}
			 else {
				 System.out.println("URL is not found within the given timeOut: " + timeOut);
				 return null;
			 }
		
		}
		
		
		public Boolean waitForTotalWindows(int totalWindowsToBe, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));
			
		}


		/**
		 * An expectation for checking that an element is present on the DOM of a page.
		 * This does not necessarily mean that the element is visible on the page.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementPresence(By locator, int timeOut) {
			WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));// sel 4.x
			return Wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}

		/**
		 * An expectation for checking that an element is present on the DOM of a page
		 * and visible on the page. Visibility means that the element is not only
		 * displayed but also has a height and width that is greater than 0.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public WebElement waitForElementVisible(By locator, int timeOut) {
			WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			WebElement element = Wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
			if(Boolean.parseBoolean(DriverFactory.highlightElement) ){
				jsUtil.flash(element);
			}
			
			return element;
			

		}

		/**
		 * An expectation for checking that all elements present on the web page that
		 * match the locator are visible on the page. Visibility means that the elements
		 * are not only displayed but also have a height and width that is greater than 0.
		 * default timeout = 500 ms
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

		}
		//default timeOut = intervalTimeOut
		public List<WebElement> waitForElementsVisible(By locator, int timeOut ,int intervalTimeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTimeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

		}


		/**
		 * An expectation for checking that there is at least one element present on a
		 * web page.
		 * 
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

		}

		/**
		 * An expectation for checking an element is visible and enabled such that you
		 * can click it.
		 * 
		 * @param locator
		 * @param timeOut
		 */
		public void clickElementWhenReady(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}

		public WebElement checkElementClickable(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.elementToBeClickable(locator));

		}
		
		//frames with wait:
		

		public void waitForFrameAndSwitchToItByIdOrName(int timeOut, String IdOrName) {
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IdOrName));
			
		}
		
		public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
			
		}
		
		public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		

		}
		
		public void waitForFrameAndSwitchToItByFrameLocator(int timeOut, By frameLocator) {
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			
		}
		
		
		
		
		
		

	}



