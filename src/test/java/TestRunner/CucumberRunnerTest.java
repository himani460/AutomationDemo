package TestRunner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(

		features="src/test/resources/Feature/TavantLogin.feature",
		glue="StepsDefination",
		plugin = "com.cucumber.listener.ExtentCucumberFormatter:",
		tags ="@Test_Id_TRANE-01",
		monochrome = true

		)


public class CucumberRunnerTest {

	static String fileName = new SimpleDateFormat("yyyyddhhmmss").format(new Date());

	@BeforeClass 
	public static void setup(){

		ExtentProperties extendProperties = ExtentProperties.INSTANCE;
		extendProperties.setReportPath("target/cucumber-reports/Report_"+fileName+".html");


	}

	@AfterClass
	public static void writeExtendReport () throws IOException{

		Reporter.loadXMLConfig("src/extent-config.xml");
	}


}




//single method -@Test_Id_WMAUATG-5809 // whole single feature file - @TAVANT_Loginpage



