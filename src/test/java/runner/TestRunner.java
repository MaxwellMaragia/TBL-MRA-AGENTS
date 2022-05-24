package runner;

import StepDefinitions.sharedatastep;
import StepDefinitions.stepDefinitions;
import Utils.BaseClass;
import org.apache.commons.mail.EmailException;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src\\test\\resources\\FeatureFiles",
		glue = "StepDefinitions" ,
		tags = "@engagement",
		dryRun = false,    //checks whether each feature has a mapped step definition
		monochrome = true,// neat output after tc run
		plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","json:target/positive/cucumber.json", "pretty", "html:target/positive/cucumber.html"}
		)


public class TestRunner{

	public TestRunner(sharedatastep sharedata) {
		stepDefinitions.sharedata = sharedata;
	}

	@AfterClass
	public static void afterSuite() throws EmailException, IOException {
		String Final_ZIP = "./Report.zip";
		String FOLDER_TO_ZIP = "./test-output";
		BaseClass.zip(FOLDER_TO_ZIP, Final_ZIP);
		String output = "Tests passed = " + sharedatastep.passed + ", Tests failed = " + sharedatastep.failed;
//		BaseClass.sendMail("Test results for MRA SIT AGENTS module automation", output, Final_ZIP);
	}
}

	