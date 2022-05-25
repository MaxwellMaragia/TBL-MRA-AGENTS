package StepDefinitions;


import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import gherkin.lexer.Th;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en_old.Ac;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.poi.ss.formula.functions.Na;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.BaseClass;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;




@RunWith(Cucumber.class)
public class stepDefinitions extends BaseClass  {
    public Properties Pro;
    public Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    public Actions actions;
    public JavascriptExecutor jse;


    public static sharedatastep sharedata;


    public stepDefinitions(sharedatastep sharedata) {

        stepDefinitions.sharedata = sharedata;

    }

    @Before(order = 2)
    public void method1() throws Exception {
        Pro = new Properties();
        FileInputStream fls = new FileInputStream("src\\test\\resources\\global.properties");
        Pro.load(fls);
        driver = BaseClass.getDriver();
        actions = new Actions(driver);
        jse = (JavascriptExecutor)driver;
    }

    public void switchToFrameBackoffice(){
        WebElement frame = wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("iframe")));
        driver.switchTo().frame(frame);
    }

    @Then("Switch to backoffice frame")
    public void switchToBoFrame() {
        switchToFrameBackoffice();
    }

    @Then("Switch to default")
    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    @Then("^Verify success message \"([^\"]*)\"$")
    public void verify_success_message(String Message) throws Throwable {

        WebElement successMessage = wait(200).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'" + Message + "')]")));
        if (successMessage.isDisplayed()) {
            System.out.println("Success message ('" + Message + "') has been displayed");
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }

    @Then("^Verify error message \"([^\"]*)\"$")
    public void verify_error_message(String error) throws Throwable {

        WebElement errorMessage = wait(20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'" + error + "')]")));
        if (errorMessage.isDisplayed()) {
            //This will scroll the page till the element is found
            System.out.println("Error message ('" + error + "') has been displayed");
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }

    @Then("^Verify no data is found in table$")
    public void verify_no_data_is_found_in_table() throws Throwable {
        WebElement noDataXpath = wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'No record(s) found.')]")));
        if (noDataXpath.isDisplayed()) {
            Assert.assertTrue("No data found in table", true);
        } else {
            Assert.assertFalse("Data found in table", false);
        }
    }

    @Given("^User navigates to the login page$")
    public void user_navigates_to_the_login_page() throws Throwable {
        driver.get(prop.getProperty("MRA_BO_URL"));
    }

    @When("^Enters the username \"([^\"]*)\" and password \"([^\"]*)\" to login$")
    public void enters_the_username_something_and_password_something_to_login(String username, String password) throws Throwable {
        wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.id("loginForm:username"))).sendKeys(username);
        Thread.sleep(350);
        driver.findElement(By.id("loginForm:password")).sendKeys(password);
        Thread.sleep(350);
        driver.findElement(By.xpath("//button[span='Login']")).click();
    }

    @Then("^User should be logged in$")
    public void user_should_be_logged_in() throws Throwable {
        String URL = driver.getCurrentUrl();

//    	Assert.assertEquals(URL, "http://18.202.88.7:8001/trips-ui/faces/login/Welcome.xhtml" );
        Assert.assertEquals(URL, "https://backoffice.mra.mw:8443/trips-ui/faces/login/Welcome.xhtml");
    }

    @Then("^User logs out successfully$")
    public void user_logs_out_successfully() throws Throwable {
        driver.findElement(By.id("Logout")).click();

    }

    //---------------------------------------------------------------------Verify the Process of Assign Audit Case-----------------------------------------------------------------------------------------------//
    @Given("^Open CRM URL Module as \"([^\"]*)\"$")
    public void open_crm_url_module_as_something(String strArg1) throws Throwable {
        driver.get("https://" + strArg1 + ":TestTechnoSM1@trips-crm.mra.mw:5555/TripsWorkflow/main.aspx");
    }

    @And("^Close Popup Window$")
    public void close_Popup_Window() throws Throwable {

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement specificframe = (driver.findElement(By.id(Pro.getProperty("CRM_ExploreCrmWindow_Frame__ID"))));
        driver.switchTo().frame(specificframe);
        WebDriverWait CloseWindow = new WebDriverWait(driver, 60);
        CloseWindow.until(ExpectedConditions.elementToBeClickable(By.id(Pro.getProperty("CRM_ExploreCrmWindow_Frame_Close_ID")))).click();
    }

    @And("^Click on Case management dropdown$")
    public void click_on_case_management_dropdown() throws Throwable {
        switch_to_frame0();
        wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Active Cases in Progress Overview')]"))).isDisplayed();
        switchToDefault();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"TabCS\"]/a/span")).click();
        Thread.sleep(1000);
    }


    @Then("^switch to frame0$")
    public void switch_to_frame0() throws Throwable {
        driver.switchTo().defaultContent();
        WebElement specificframe = wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.id(Pro.getProperty("NextStage_Frame_ID"))));
        driver.switchTo().frame(specificframe);
        Thread.sleep(3000);

    }

    @Then("^switch to frame1$")
    public void switch_to_frame1() throws Throwable {
        driver.switchTo().defaultContent();
        WebElement specificframe = wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.id(Pro.getProperty("NextStage_Frame_ID1"))));
        driver.switchTo().frame(specificframe);
        Thread.sleep(3000);

    }

    @Then("^Enter Outcome Reason$")
    public void enter_Outcome_Reason() throws Throwable {
        Thread.sleep(2000);
        WebElement specificframe = (driver.findElement(By.id(Pro.getProperty("OutComeReason_Frame_XPATH"))));
        driver.switchTo().frame(specificframe);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.xpath(Pro.getProperty("NextStage_RefNum_Reject_OutComeReason_XPATH"))).click();
        WebDriverWait ReasonValue = new WebDriverWait(driver, 60);
        ReasonValue.until(ExpectedConditions.elementToBeClickable(By.xpath(Pro.getProperty("NextStage_RefNum_Reject_OutComeReason_Options_XPATH")))).click();
        Thread.sleep(8000);
    }


    @Then("Open CRM and close modal")
    public void openCRMAndCloseModal() {
        driver.get(Pro.getProperty("NRA_CRM_URL"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement specificframe = (driver.findElement(By.id(Pro.getProperty("CRM_ExploreCrmWindow_Frame__ID"))));
        driver.switchTo().frame(specificframe);
        WebDriverWait CloseWindow = new WebDriverWait(driver, 60);
        CloseWindow.until(ExpectedConditions.elementToBeClickable(By.id(Pro.getProperty("CRM_ExploreCrmWindow_Frame_Close_ID")))).click();
    }

    @Then("search for reference number")
    public void searchForReferenceNumber() throws InterruptedException {
        WebElement search = wait(20).until(ExpectedConditions.visibilityOfElementLocated(By.id("crmGrid_findCriteria")));

        search.clear();
        Thread.sleep(2000);
        //search.sendKeys("*AV/000000875/2021");
        search.sendKeys(sharedatastep.Ref);
        Thread.sleep(2000);
        search.sendKeys(Keys.ENTER);

        Thread.sleep(2000);
    }

    @Then("^Click on reference number$")
    public void click_on_reference_number() {

        WebElement elementLocator = wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr/td[1]")));

        Actions actions = new Actions(driver);
        actions.doubleClick(elementLocator).perform();

        driver.switchTo().defaultContent();

    }

    public boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for (int i = 0; i < Objects.requireNonNull(dirContents).length; i++) {
            if (dirContents[i].getName().equals(fileName)) {
                // File has been found, it can now be deleted:
                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    @Then("^Verify file \"([^\"]*)\" has been downloaded in downloads directory \"([^\"]*)\"$")
    public void verify_file_has_been_downloaded_in_downloads_directory(String fileName, String downloadPath) throws Throwable {
        Thread.sleep(10000);
        if (isFileDownloaded(downloadPath, fileName)) {
            System.out.println(fileName + ": has been downloaded");
            Assert.assertTrue(true);
        } else {
            Assert.assertFalse(fileName + ": has not been downloaded", false);
        }
    }

    @And("click on Queues")
    public void clickOnQueues() {
        driver.findElement(By.xpath("//*[text()='Queues']")).click();
    }

    @And("^pick the case$")
    public void pick_the_case() throws Throwable {
        WebElement pickButton = wait(60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Pick ']")));
        Actions actions = new Actions(driver);
        actions.doubleClick(pickButton).perform();
    }

    @And("^click pick button dropdown$")
    public void click_pick_button_dropdown() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement assignDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("moreCommands")));
        assignDropdown.click();

        WebElement pickButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("queueitem|NoRelationship|HomePageGrid|tbg.queueitem.HomepageGrid.Pick")));
        pickButton.click();
    }

    @And("enters reference number in search results")
    public void entersReferenceNumberInSearchResults() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("crmGrid_findCriteria")));

        search.clear();
        Thread.sleep(1000);

        //search.sendKeys("*IN/000036357/2022");
        search.sendKeys("*"+sharedatastep.Ref);
        Thread.sleep(5000);
        search.sendKeys(Keys.ENTER);

        Thread.sleep(1000);
    }


    @And("wait for plan to load {string}")
    public void waitForPlanToLoad(String arg0) {
        WebDriverWait wait = new WebDriverWait(driver, 200);
        WebElement frame = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WebResource_AgentRegistrationAngular")));
        driver.switchTo().frame(frame);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='" + arg0 + "']")));
    }


    @And("Click on Save button")
    public void clickOnSaveButton() throws InterruptedException {
        Thread.sleep(1000);
        driver.switchTo().defaultContent();
        driver.findElement(By.id("tbg_agentregistrationapplication|NoRelationship|Form|Mscrm.Form.tbg_agentregistrationapplication.Save")).click();
    }

    @Given("User navigates to portal")
    public void userNavigatesToPortal() {
        driver.get(Pro.getProperty("MRA_Portal_URL"));
        driver.manage().window().maximize();
    }

    @Then("Click tax agent")
    public void clickTaxAgent() {
        wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Tax Agent']"))).click();
    }
    @Then("Click taxpayer")
    public void clickTaxpayer() {
        wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Taxpayer']"))).click();
    }

    @Then("Enters the username {string} and password {string} to login to portal")
    public void entersTheUsernameAndPasswordToLoginToPortal(String username, String password) {

        wait(60).until(ExpectedConditions.visibilityOfElementLocated(By.id("id_userName"))).sendKeys(username);
        wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.id("id_password"))).sendKeys(password);
        driver.findElement(By.id("btnSubmit")).click();

    }

    @Then("Click register as agent button in portal home screen")
    public void clickRegisterAsAgentButtonInPortalHomeScreen() {
        WebElement field = wait(40).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Register As Agent']")));
        jse.executeScript("arguments[0].click()", field);
    }

    @Then("Verify portal success message {string}")
    public void verifyPortalSuccessMessage(String arg0) {
        WebElement successMessage = wait(200).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + arg0 + "')]")));
        if (successMessage.isDisplayed()) {
            System.out.println("Success message ('" + arg0 + "') has been displayed");
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }

    @Then("Obtain ARN for portal agent registration {string}")
    public void obtainARNForPortalAgentRegistration(String arg0) throws InterruptedException {
        String message = wait(100).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + arg0 + "')]"))).getText();
        //Your tax Agent registration request has been successfully submitted. Your reference number is : ARN/00058993/2022

        System.out.println(message);
        System.out.println("Agent Registration Reference Number is : " +message.substring(96));
        sharedatastep.Ref = message.substring(96);
        Thread.sleep(1000);
    }

    @Then("Agent registration status should be {string}")
    public void agentRegistrationStatusShouldBe(String arg0) throws InterruptedException {
        driver.switchTo().frame("contentIFrame1");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        Thread.sleep(3000);
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='" + arg0 + "']"))).getText();
        Assert.assertEquals(arg0, text);
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
    }

    @Then("Approve application")
    public void approveApplication() throws Throwable {
        driver.switchTo().defaultContent();

        switch_to_frame1();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//div[@data-attributename='tbg_approvaloutcome']")).click();
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }


    @Then("Navigate to My clients > New Engagement request")
    public void navigateToMyClientsNewEngagementRequest() {
        WebElement myClients = wait(40).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='MY CLIENTS']")));
        jse.executeScript("arguments[0].click()", myClients);
        WebElement newEngagementRequest =wait(40).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='New Engagement Request']")));
        jse.executeScript("arguments[0].click()", newEngagementRequest);
    }

    @Then("Enter taxpayers tin as {string} and engagement request details")
    public void enterTaxpayersTinAsAndEngagementRequestDetails(String arg0) throws InterruptedException {
        WebElement tinField = wait(50).until(ExpectedConditions.elementToBeClickable(By.id("id_tin")));
        jse.executeScript("arguments[0].scrollIntoView(true);", tinField);
        tinField.sendKeys(arg0);
        Thread.sleep(900);
        driver.findElement(By.id("id_requestText")).sendKeys("Hello dear taxpayer, I would like to be your tax agent, please accept my request");
    }

    @Then("Click submit")
    public void clickSubmit() {
        driver.findElement(By.xpath("id_btnSubmit")).click();
    }

    @Then("Verify portal alert success message {string}")
    public void verifyPortalAlertSuccessMessage(String arg0) {
        WebElement successMessage = wait(200).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'" + arg0 + "')]")));
        if (successMessage.isDisplayed()) {
            System.out.println("Success message ('" + arg0 + "') has been displayed");
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }

    @Then("Navigate to My Tax > Engagement notifications")
    public void navigateToMyTaxEngagementNotifications() {
        WebElement myTax = wait(40).until(ExpectedConditions.elementToBeClickable(By.id("id_btnMyTax")));
        jse.executeScript("arguments[0].click()", myTax);
        WebElement engagementNotifications =wait(40).until(ExpectedConditions.elementToBeClickable(By.id("id_btnEngagementNotifications")));
        jse.executeScript("arguments[0].click()", engagementNotifications);
    }

    @Then("Click respond")
    public void clickRespond() {
        jse.executeScript("arguments[0].click()",wait(30).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Respond']"))));
    }

    @Then("Select approve")
    public void selectApprove() {
        jse.executeScript("arguments[0].click()",wait(30).until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Approve']"))));
    }

    @Then("Select taxtype")
    public void selectTaxtype() throws InterruptedException {
        jse.executeScript("arguments[0].click()",wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"id_engagementResponseForm\"]/div/div[1]/div[2]/div/tb-dropdown/div/div[2]/p-dropdown/div/div[3]"))));
        Thread.sleep(500);
        driver.findElement(By.xpath("//li[span='PAYE']")).click();
        Thread.sleep(500);

    }

    @Then("Select allowable action {string}")
    public void selectAllowableAction(String arg0) throws InterruptedException {
        Thread.sleep(500);
        wait(20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[span='"+arg0+"']/following-sibling::td/span[2]/p-checkbox/div/div[2]"))).click();
    }

    @Then("Navigate to My clients")
    public void navigateToMyClients() {
        WebElement myClients = wait(40).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='MY CLIENTS']")));
        jse.executeScript("arguments[0].click()", myClients);
    }

    @Then("Select taxpayer")
    public void selectTaxpayer() {
        jse.executeScript("arguments[0].click()",wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/trips-app/div/ng-component/div/app-view-engagements-list/div/p-table/div/div[1]/table/tbody/tr/td[1]/span[2]/div/p-tableradiobutton/div/div[2]"))));
    }

    @Then("Click on manage client account")
    public void clickOnManageClientAccount() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Manage Client Account']")).click();
    }

    @Then("Click on Submit a return")
    public void clickOnSubmitAReturn() {
        jse.executeScript("arguments[0].click()",wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.id("id_btnSubmitAReturn"))));
    }

    @Then("Select taxtype as {string}")
    public void selectTaxtypeAs(String taxtype) throws InterruptedException {
        wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),' Returns Summary ')]")));
        Thread.sleep(1000);
        WebElement TaxTypeSelector = wait(20).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[span='"+taxtype+"']/preceding-sibling::td[1]/span//span")));
        Thread.sleep(1000);
        TaxTypeSelector.click();
    }

    @Then("Select period as {string}")
    public void selectPeriodAs(String arg0) throws InterruptedException {
        Thread.sleep(1000);
        WebElement TaxTypeSelector = wait(30).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[span='"+arg0+"']/preceding-sibling::td[1]/span//span")));
        Thread.sleep(1000);
        TaxTypeSelector.click();
    }

    @Then("Click continue")
    public void clickContinue() throws InterruptedException {
        Thread.sleep(1000);
        jse.executeScript("arguments[0].click()",driver.findElement(By.id("btnContinue")));
    }

    @Then("Upload PAYE template")
    public void uploadPAYETemplate() throws InterruptedException, AWTException {
        WebElement element = wait(20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tb-returns-csv-upload/div[label='PAYE Employee Emoluments Upload']/div//div//span")));
        jse.executeScript("arguments[0].scrollIntoView()", element);

        String path = System.getProperty("user.dir") + File.separator + "src\\test\\resources" + File.separator + "PAYE_template.csv";
        element.click();
        Thread.sleep(1000);
        Robot rb = new Robot();

        // copying File path to Clipboard
        System.out.println("The path is : "+path);
        StringSelection str = new StringSelection(path);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
        Thread.sleep(1000);

        // press Contol+V for pasting
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        // release Contol+V for pasting
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);
        Thread.sleep(500);
        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(600);
    }

    @Then("Verify alert success message {string}")
    public void verifyAlertSuccessMessage(String arg0) {
        WebElement message = wait(100).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'"+arg0+"' )]")));
        Assert.assertTrue(message.isDisplayed());
    }

    @Then("Confirm information given for paye tax is true")
    public void confirmInformationGivenForPayeTaxIsTrue() throws InterruptedException {
        Thread.sleep(500);
        WebElement element = wait(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"id_payeReturnForm\"]/div[6]/tb-checkbox/div/div[2]/p-checkbox/div/div[2]")));
        jse.executeScript("arguments[0].scrollIntoView()", element);
        element.click();
    }

    @Then("Submit PAYE Tax returns")
    public void submitPAYETaxReturns() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
    }

    @Then("Verify success message displayed as {string}")
    public void verifySuccessMessageDisplayedAs(String arg0) {
        WebElement message = wait(100).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + arg0 + "')]")));
        Assert.assertTrue(message.isDisplayed());
    }

    @Then("Click on manage client engagements")
    public void clickOnManageClientEngagements() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Manage Engagement']")).click();
    }

    @Then("Assign engagement {string} to associates")
    public void assignEngagementToAssociates(String arg0) throws InterruptedException {
        WebElement element = wait(40).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[span='"+arg0+"']/following-sibling::td[2]//span[2]/p-celleditor/p-dropdown/div/label")));
        jse.executeScript("arguments[0].click()", element);
        Thread.sleep(1000);
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    @Then("Submit engagement changes")
    public void submitEngagementChanges() {
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
    }

    @Then("Navigate to My Tax > My agent")
    public void navigateToMyTaxMyAgent() {
        WebElement myTax = wait(40).until(ExpectedConditions.elementToBeClickable(By.id("id_btnMyTax")));
        jse.executeScript("arguments[0].click()", myTax);
        WebElement engagementNotifications =wait(40).until(ExpectedConditions.elementToBeClickable(By.id("id_btnMyAgents")));
        jse.executeScript("arguments[0].click()", engagementNotifications);
    }

    @Then("Select agent from dropdown")
    public void selectAgentFromDropdown() throws InterruptedException {
        jse.executeScript("arguments[0].click()", wait(30).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"id_TaxpayerEngmntMgmtForm\"]/div[1]/tb-dropdown/div/div[2]/p-dropdown/div/div[3]"))));
        Thread.sleep(1000);
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    @Then("Select engagement details taxtype")
    public void selectEngagementDetailsTaxtype() throws InterruptedException {
        jse.executeScript("arguments[0].click()", wait(30).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"id_TaxpayerEngmntMgmtForm\"]/div[4]/div/div/tb-dropdown/div/div[2]/p-dropdown/div/div[3]"))));
        Thread.sleep(1000);
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }
}



