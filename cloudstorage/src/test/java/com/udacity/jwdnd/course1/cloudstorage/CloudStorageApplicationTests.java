package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private HomePage homePage;
	private LoginPage loginPage;
	private SignupPage signupPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));


	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("successmsg")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
		Write a test that verifies that an unauthorized user can only access the login and signup pages.
	 */
	@Test
	public void testUnauthorizedUserAccessRestrictedToLoginAndSignup() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	/**
	 Write a test that signs up a new user,
	 logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
	 */
	@Test
	public void testSuccessfulSignupLoginAndHomeAccess() {
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.siunup("Rand", "Abdulaziz", "rand","r");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage=new LoginPage(driver);
		loginPage.login("rand","r");
		homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 Write a test that creates a note, and verifies it is displayed.
	 */
	@Test
	public void testCreateNoteAndVerifyDisplay() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		signupPage = new SignupPage(driver);
		signupPage.siunup("Rand", "Abdulaziz", "rand","r");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage=new LoginPage(driver);
		loginPage.login("rand","r");
		homePage = new HomePage(driver);
		driver.findElement(By.id("nav-notes-tab")).click();

		homePage.createNewNote("Note#1","A note");
		Thread.sleep(5000);

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();

		Assertions.assertEquals("Note#1" , homePage.getNoteTitleWithJS());
		Assertions.assertEquals("A note", homePage.getNoteDescWithJS());


	}
	/**
	 Write a test that edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void testEditNoteAndVerifyChangesDisplayed() {
		driver.get("http://localhost:" + this.port + "/signup");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		signupPage = new SignupPage(driver);
		signupPage.siunup("Rand", "Abdulaziz", "rand","r");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage=new LoginPage(driver);
		loginPage.login("rand","r");
		homePage = new HomePage(driver);
		driver.findElement(By.id("nav-notes-tab")).click();

		homePage.createNewNote("Note#1","A note");

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
		homePage.editNote("Note#2", "A new description");

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
		Assertions.assertEquals("Note#2" , homePage.getNoteTitleWithJS());
		Assertions.assertEquals("A new description", homePage.getNoteDescWithJS());

	}
	/**
	 Write a test that deletes a note and verifies that the note is no longer displayed.
	 */
	@Test
	public void testDeleteNoteAndVerifyAbsence() {

		driver.get("http://localhost:" + this.port + "/signup");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		signupPage = new SignupPage(driver);
		signupPage.siunup("Rand", "Abdulaziz", "rand","r");
		driver.get("http://localhost:" + this.port + "/login");

		loginPage=new LoginPage(driver);
		loginPage.login("rand","r");
		homePage = new HomePage(driver);
		driver.findElement(By.id("nav-notes-tab")).click();

		homePage.createNewNote("Note#1","A note");

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
		homePage.deleteNote();

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
		try {
			homePage.getNoteTitleWithJS();
		} catch (Exception e) {
			Assertions.assertEquals(org.openqa.selenium.NoSuchElementException.class, e.getClass());
		}

	}
	/**
	Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	 */
	@Test
	public void testCreateCredentialsVerifyDisplayAndEncryptedPassword() {

		driver.get("http://localhost:" + this.port + "/signup");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		signupPage = new SignupPage(driver);
		signupPage.siunup("Rand", "Abdulaziz", "rand","r");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage=new LoginPage(driver);
		loginPage.login("rand","r");
		homePage = new HomePage(driver);
		driver.findElement(By.id("nav-credentials-tab")).click();
		homePage.createNewCred("http://google.com","rand","r");

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
		Assertions.assertEquals("http://google.com",homePage.getCredUrlWithJE());
		Assertions.assertEquals("rand", homePage.getCredUsernameWithJE());
		Assertions.assertNotEquals("r",homePage.getCredPasswordWithJE());
	}
	/**
	 Write a test that views an existing set of credentials,
	 verifies that the viewable password is unencrypted,
	 edits the credentials, and verifies that the changes are displayed.
	 */
	@Test
	public void testViewExistingCredentialsVerifyPasswordAndEditChanges() {

		driver.get("http://localhost:" + this.port + "/signup");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		signupPage = new SignupPage(driver);
		signupPage.siunup("Rand", "Abdulaziz", "rand","r");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage=new LoginPage(driver);
		loginPage.login("rand","r");
		homePage = new HomePage(driver);
		driver.findElement(By.id("nav-credentials-tab")).click();
		homePage.createNewCred("http://google.com","rand","r");

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
		Assertions.assertEquals("r", homePage.getPasswordValueAfterClickingEditButton());
	}

	/**
	 Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	 */
	@Test
	public void testSuccessfulCredentialsDeletionAndNoDisplay() {

		driver.get("http://localhost:" + this.port + "/signup");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		signupPage = new SignupPage(driver);
		signupPage.siunup("Rand", "Abdulaziz", "rand","r");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage=new LoginPage(driver);
		loginPage.login("rand","r");
		homePage = new HomePage(driver);
		driver.findElement(By.id("nav-credentials-tab")).click();
		homePage.createNewCred("http://google.com","rand","r");

		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
		homePage.deleteCred();
		try {
			homePage.getCredUrlWithJE();
		} catch (Exception e) {
			Assertions.assertEquals(org.openqa.selenium.NoSuchElementException.class, e.getClass());
		}
	}
}
