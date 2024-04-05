
package commonFunctions;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
public static WebDriver startBrowser()throws Throwable
{
	conpro=new Properties();
	conpro.load(new FileInputStream("./PropertyFiles\\Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
			driver.manage().window().maximize();
	
}else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver=new FirefoxDriver();
	
}else {
	Reporter.log("browser value not matching",true);
}
	return driver;
}
public static void openUrl() {
	driver.get(conpro.getProperty("Url"));
}
public static void waitForElement(String LocatorType,String LocatorValue,String TestData) {
	WebDriverWait mywait=new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
	if(LocatorType.equalsIgnoreCase("name")) {
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("xpath")) {
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("id")) {
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
}
public static void typeAction(String LocatorType,String LocatorValue,String TestData) {
if(LocatorType.equalsIgnoreCase("xpath")) {
	driver.findElement(By.xpath(LocatorValue)).clear();
	driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
}
if(LocatorType.equalsIgnoreCase("name")) {
	driver.findElement(By.name(LocatorValue)).clear();
	driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
}
if(LocatorType.equalsIgnoreCase("id")) {
	driver.findElement(By.id(LocatorValue)).clear();
	driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
}
}
public static void clickAction(String LocatorType,String LocatorValue) {
	if(LocatorType.equalsIgnoreCase("xpath")) {
		driver.findElement(By.xpath(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("name")) {
		driver.findElement(By.name(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("id")) {
		driver.findElement(By.id(LocatorValue)).click();
	}
}
public  static void validateTitle(String Expected_title) {
	String Actual_title=driver.getTitle();
	try {
	Assert.assertEquals(Actual_title,Expected_title,"Title is not Matching");
	}
	catch(AssertionError a) {
	System.out.println(a.getMessage());
}
}
public static void closeBrowser() {
	driver.quit();
}
public static String generateDtae() {
	Date date=new Date();
	DateFormat df=new SimpleDateFormat("YYYY_MM_dd hh_mm");
	return df.format(date);
}
public static void dropDownAction(String LocatorType,String LocatorValue,String TestData)
{
if(LocatorType.equalsIgnoreCase("id")) 
{
	int value=Integer.parseInt(TestData);
	Select element=new Select(driver.findElement(By.id(LocatorValue)));
	element.selectByIndex(value);
}
if(LocatorType.equalsIgnoreCase("xpath")) 
{
	int value=Integer.parseInt(TestData);
	Select element=new Select(driver.findElement(By.xpath(LocatorValue)));
	element.selectByIndex(value);
}
if(LocatorType.equalsIgnoreCase("name")) 
{
	int value=Integer.parseInt(TestData);
	Select element=new Select(driver.findElement(By.name(LocatorValue)));
	element.selectByIndex(value);
}
}
public static void captureStock(String LocatorType,String LocatorValue) throws Throwable {
	String stock_Num="";
	if(LocatorType.equalsIgnoreCase("id")) {
		stock_Num=driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name")) {
		stock_Num=driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("xpath")) {
		stock_Num=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
FileWriter fw=new FileWriter("./\\CaptureData/stockNumber.txt");
BufferedWriter bw=new BufferedWriter(fw);
bw.write(stock_Num);
bw.flush();
bw.close();
}
public static void stockTable() throws Throwable {
	FileReader fr=new FileReader("./\\CaptureData/stockNumber.txt");
	BufferedReader br=new BufferedReader(fr);
	String Exp_Data=br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) 
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	
String Act_Data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_itemslist']/tbody/tr[1]/td[8]/div/span/span")).getText();
Reporter.log(Act_Data+"   "+Exp_Data,true);
		try {
			Assert.assertEquals(Act_Data,Exp_Data,"stock number is not matching");
		}
		catch(AssertionError a) {
			System.out.println(a.getMessage());
			}
}
}
