package StepsDefination;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import de.redsix.pdfcompare.PdfComparator;
//import gherkin.formatter.Reporter;
import junit.framework.Assert;

public class TavantLoginPage {
	public static WebDriver driver;
	public static String globalfilename;
	public static String Testid;


	@Before
	public void getTagNames(Scenario scenario)
	{
		// scenario @Test_Id_TRANE-01 @TRANE_Loginpage

		for(String tag : scenario.getSourceTagNames()){
			System.out.println(scenario.getSourceTagNames());
			try{
				if(tag.startsWith("@Test_Id_")){
					Testid =tag.split("Test_Id_")[1];
					System.out.println(Testid);
					//TRANE-01==== Testid

				}
			}
			catch(Exception e){

				System.out.println("No tag starts with Test_Id_");
			}
		}
	}

	@Given("^user launches browser in chrome$")
	public void user_launches_browser_in_chrome() throws Throwable {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\himani.sengar\\chromedriver.exe");      
		driver=new ChromeDriver();



	}

	@Given("^user loads fixture \"([^\"]*)\"$")
	//filename = Test_DataIP
	public static Sheet user_loads_fixture(String filename) throws Throwable {

		File file = new File ("src/test/resources/Fixture/" + filename);
		FileInputStream fis=new FileInputStream(file);  
		//FileInputStream
		Workbook inputStream = new XSSFWorkbook(fis);
		Sheet sh = inputStream.getSheet("sheet1");
		//InputStream is used to read data from a source
		//System.out.println(sh);
		globalfilename = file.getName();
		//System.out.println(globalfilename);
		//Getting the sheet Number(sheet1) in sh 
		return sh;

	}

	public static String getColumnValueFromFixture(String columnName ) throws Throwable{

		int rowCount = 0;
		int columnCount = 0;
		String finalOutput = null;
		rowCount = (user_loads_fixture(globalfilename).getLastRowNum()) - (user_loads_fixture(globalfilename).getFirstRowNum());
		/*  1-0=1*/
		int p = (user_loads_fixture(globalfilename).getLastRowNum());
		/*System.out.println(p); p=1*/
		int o = (user_loads_fixture(globalfilename).getFirstRowNum());
		/*System.out.println(o); o=0*/
		Row r =	user_loads_fixture(globalfilename).getRow(0);
		int k = r.getLastCellNum(); 
		/*System.out.println(k);*/ // 7 /*getting all the data of row 1*/
		for(int j=0; j< r.getLastCellNum(); j++){    /*getLastCellNum= 7*/
			Cell c = r.getCell(j); 
			if(c.getStringCellValue().equals(columnName)){
				/*System.out.println(c.getStringCellValue());*/
				// columnName= SSOLoginURL//2, c.getStringCellValue()  = SSOLoginURL// 2

				columnCount=j; //j=2 columnCount=2
				break;
			}
		}
		DataFormatter formatter = new DataFormatter();
		for(int i =0; i< rowCount + 1; i++)  // rowCount =1 
		{
			Row row = user_loads_fixture(globalfilename).getRow(i);
			if(formatter.formatCellValue(row.getCell(0)).equals(Testid)){
				/*System.out.println(row.getCell(0));*///WMAUATG-5808
				finalOutput = row.getCell(columnCount).getStringCellValue();//2//http://10.132.201.33:8080/login.action
				/*System.out.println(finalOutput);*/
			}
		}
		return finalOutput;
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception{

		String dateName = new SimpleDateFormat("yyyyddhhmmss").format(new Date());
		//System.out.println(dateName);
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destination = "target/cucumber-reports/TakeScreenshots/" + screenshotName + "/" + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination.split("target/cucumber-reports/")[1];


	}



	@And("^user enters url as \"([^\"]*)\"$")
	public void user_enters_url_as(String url) throws Throwable {

		url= getColumnValueFromFixture("ApplicationURL");
		/*System.out.println(url);*/
		driver.get(url);
		/*System.out.println(getScreenshot(driver, Testid));*/
		//getScreenshot(driver, Testid);
		Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));


	}


	@And("^user enters text \"([^\"]*)\" in the textbox with attribute \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void user_enters_text_in_the_textbox_with_attribute_and_value(String text, String textattribute, String textvalue) throws Throwable {
		try{
			if(text.startsWith("#"))
			{
				text = getColumnValueFromFixture(text.split("#")[1]);
				//System.out.println(text);
			}
			WebElement webElement = driver.findElement(By.xpath("//input[@" + textattribute + "='" + textvalue + "']"));
			webElement.sendKeys(text);
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
		catch(Exception e)
		{
			Assert.fail(e.getMessage());
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
	}


	@And("^user enters textarea \"([^\"]*)\" in the textbox with attribute \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void user_enters_textarea_in_the_textbox_with_attribute_and_value(String text, String textattribute, String textvalue) throws Throwable {
		try{
			if(text.startsWith("#"))
			{
				text = getColumnValueFromFixture(text.split("#")[1]);
				//System.out.println(text);
			}
			WebElement webElement = driver.findElement(By.xpath("//textarea[@" + textattribute + "='" + textvalue + "']"));
			webElement.sendKeys(text);
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
		catch(Exception e)
		{
			Assert.fail(e.getMessage());
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
	}



	@Given("^user click element with value \"([^\"]*)\"$")
	public void user_click_element_with_value(String textvalue) throws Throwable {
		try{
			WebElement webElement = driver.findElement(By.id("" + textvalue + ""));
			webElement.click();
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
		catch(Exception e)
		{

			Assert.fail(e.getMessage());
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
	}



	@And("^user click element with attribute \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void user_click_element_with_attribute_and_value(String select, String text, String textvalue) throws Throwable {

		try{
			WebElement webElement = driver.findElement(By.xpath("//"+ select +"[@" + text + "='" + textvalue + "']"));
			//System.out.println(webElement);
			webElement.click();
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
		catch(Exception e)
		{

			Assert.fail(e.getMessage());
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
	}


	@Given("^user click element with select \"([^\"]*)\" and attribute \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void user_click_element_with_select_and_attribute_and_value(String select, String text, String textvalue) throws Throwable {

		try{
			WebElement webElement = driver.findElement(By.xpath("//"+ select +"[@" + text + "='" + textvalue + "']"));
			//System.out.println(webElement);
			webElement.click();
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));

		}
		catch(Exception e)
		{

			Assert.fail(e.getMessage());
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}
	}


	@And("^user switch to frame with value \"([^\"]*)\"$")
	public void user_switch_to_frame_with_value(String textvalue) throws Throwable {
		driver.switchTo().frame(textvalue);
	}


	@And("^user maximize the window$")
	public void user_maximize_the_window() throws Throwable {
		driver.manage().window().maximize();

	}

	private static String getYesterdayDay()
	{
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		//System.out.println("Today Int: " + todayInt +"\n");
		int yesterday=todayInt-1;
		String yesterdayDate = Integer.toString(yesterday);
		//System.out.println("Today Str: " + yesterdayDate + "\n");

		return yesterdayDate;
	}


	@And("^user input the model number$")
	public void user_input_the_model_number() throws Throwable {
		driver.findElement(By.xpath("//*[@class='dgrid-cell dgrid-cell-padding dgrid-column-inventoryTable_col_ofType_model_name field-ofType model name dgrid-filterable']//input")).sendKeys("V320 MAX 50");
		Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
	}

	@And("^user select the previous date$")
	public void user_select_the_previous_date() throws Throwable {
		String Previousday = getYesterdayDay();
		By mySelector = By.xpath("//*[@class='dijitCalendarContainer dijitCalendar dijitCalendarFocused dijitFocused']/tbody/tr/td");
		List<WebElement> calenderpopup =driver.findElements(mySelector);

		for (WebElement cell : calenderpopup)
		{
			if (cell.getText().equals(Previousday))
			{ 
				cell.click();
				break;
			}
		}
	}


	@Given("^user switch to default window$")
	public void user_switch_to_default_window() throws Throwable {
		driver.switchTo().defaultContent();
	}

	@And("^user apply the wait of (\\d+)$")
	public void user_apply_the_wait_of(long textvalue) throws Throwable {

		driver.manage().timeouts().implicitlyWait(textvalue,TimeUnit.SECONDS) ;

	}


	@And("^user scroll to the element with value \"([^\"]*)\"$")
	public void user_scroll_to_the_element_with_value(String textvalue) throws Throwable {

		try{

			WebElement webElement = driver.findElement(By.id("" + textvalue + ""));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());


		}

	}

	@And("^user select the radio button value with \"([^\"]*)\"$")
	public void user_select_the_radio_button_value_with(String textvalue) throws Throwable {

		try{
			WebElement webElement = driver.findElement(By.id("" + textvalue + ""));
			if(!webElement.isSelected())
			{
				webElement.click();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());


		}


	}

	@And("^user clear the textbox with value \"([^\"]*)\"$")
	public void user_clear_the_textbox_with_value(String textvalue) throws Throwable {
		try{
			WebElement webElement = driver.findElement(By.id("" + textvalue + ""));
			webElement.clear();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	@And("^user select the from dropdown with attribute \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void user_select_the_from_dropdown_with_attribute_and_value(String textattribute, String textvalue) throws Throwable {

		try{
			WebElement webElement = driver.findElement(By.xpath("//input[@" + textattribute + "='" + textvalue + "']"));
			webElement.click();

		}


		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	@Given("^user click element with attribute \"([^\"]*)\" and value \"([^\"]*)\" and index (\\d+)$")
	public void user_click_element_with_attribute_and_value_and_index(String textattribute, String textvalue, int index) throws Throwable {

		try{
			WebElement webElement = driver.findElement(By.xpath("(//*[@" + textattribute + "='" + textvalue + "'])[" + index + "]"));
			System.out.println(webElement);
			webElement.click();
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));

		}


		catch(Exception e)
		{
			System.out.println(e.toString());
			Reporter.addScreenCaptureFromPath(getScreenshot(driver, Testid));
		}

	}
}







