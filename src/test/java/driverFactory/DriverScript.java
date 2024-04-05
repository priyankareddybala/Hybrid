package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
String inputpath="./FileInput\\DataEngine.xlsx";
String outputpath="./FileOutput/HybridResults.xlsx";
ExtentReports report;
ExtentTest logger;
String Sheet="MasterTestCases";
WebDriver driver;
public void startTest()throws Throwable {
	
String Modulestatus="";
ExcelFileUtil xl=new ExcelFileUtil(inputpath);
for(int i=1;i<=xl.rowCount(Sheet);i++) {
	if(xl.getCellData(Sheet,i,2).equalsIgnoreCase("Y")) {
	String TCModule=xl.getCellData(Sheet,i,1);
	report=new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDtae()+".html");
	logger=report.startTest(TCModule);
	logger.assignAuthor("priya");
	for(int j=1;j<=xl.rowCount(TCModule);j++) 
	{
		String Description=xl.getCellData(TCModule,j,0);
		String ObjectType=xl.getCellData(TCModule,j,1);
		String LocatorType=xl.getCellData(TCModule,j,2);
		String LocatorValue=xl.getCellData(TCModule,j,3);
		String TestData=xl.getCellData(TCModule,j,4);
		try {
			if(ObjectType.equalsIgnoreCase("startBrowser")) {
				driver=FunctionLibrary.startBrowser();
				logger.log(LogStatus.INFO, Description);
			}
			if(ObjectType.equalsIgnoreCase("openUrl")){
				FunctionLibrary.openUrl();
				logger.log(LogStatus.INFO, Description);
}
			if(ObjectType.equalsIgnoreCase("waitForElement")){
				FunctionLibrary.waitForElement(LocatorType, LocatorValue, TestData);
				logger.log(LogStatus.INFO, Description);
}
			if(ObjectType.equalsIgnoreCase("typeAction")){
				FunctionLibrary.typeAction(LocatorType, LocatorValue, TestData);
				logger.log(LogStatus.INFO, Description);

			}
			if(ObjectType.equalsIgnoreCase("clickAction")){
				FunctionLibrary.clickAction(LocatorType, LocatorValue);
				logger.log(LogStatus.INFO, Description);

			}
			if(ObjectType.equalsIgnoreCase("validateTitle")){
				FunctionLibrary.validateTitle(TestData);
				logger.log(LogStatus.INFO, Description);

			}
			if(ObjectType.equalsIgnoreCase("closeBrowser")){
				FunctionLibrary.closeBrowser();
				logger.log(LogStatus.INFO, Description);
				}
			if(ObjectType.equalsIgnoreCase("dropDownAction")) {
				FunctionLibrary.dropDownAction(LocatorType,LocatorValue,TestData);
				logger.log(LogStatus.INFO,Description);
			}
		if(ObjectType.equalsIgnoreCase("captureStock")) {
			FunctionLibrary.captureStock(LocatorType, LocatorValue);
			logger.log(LogStatus.INFO,Description);
		}
		if(ObjectType.equalsIgnoreCase("stocktable")) {
			FunctionLibrary.stockTable();
			logger.log(LogStatus.INFO,Description);
		}
			xl.setCellData(TCModule, j, 5, "Pass", outputpath);
			logger.log(LogStatus.PASS, Description);
				Modulestatus="True";
		}catch(Exception e) {
			System.out.println(e.getMessage());
			xl.setCellData(TCModule, j, 5, "Fail", outputpath);
			logger.log(LogStatus.FAIL, Description);

			Modulestatus="False";
		}
		if(Modulestatus.equalsIgnoreCase("True")) {
			xl.setCellData(Sheet,i,3,"Pass",outputpath);
		}
		if(Modulestatus.equalsIgnoreCase("False")) {
			xl.setCellData(Sheet,i,3,"Fail",outputpath);
		}
		report.endTest(logger);
		report.flush();
	}	
	}		
	
	else {
		xl.setCellData(Sheet, i, 3, "Blocked",outputpath);
	}
	}
}
}
