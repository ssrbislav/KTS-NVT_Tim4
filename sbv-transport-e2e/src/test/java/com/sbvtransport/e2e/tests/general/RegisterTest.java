package com.sbvtransport.e2e.tests.general;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbvtransport.e2e.pages.general.LoginPage;
import com.sbvtransport.e2e.pages.general.RegisterPage;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterTest {

  private WebDriver browser;

  RegisterPage registerPage;

  @Before
  public void setupSelenium() {
    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    browser = new ChromeDriver();
    browser.manage().window().maximize();
    browser.navigate().to("localhost:4200/mainPage");
    WebElement registerButton = browser.findElement(By.cssSelector("body > app-root > app-main-page > app-header > nav > div > div.collapse.navbar-collapse > ul > ul > li:nth-child(2) > a"));
    registerButton.click();
    registerPage = PageFactory.initElements(browser, RegisterPage.class);
  }

  @Test
  public void registerTest_User_OK() throws InterruptedException {
    registerPage.setEmail("adgagadgfsdsassssddadfsga@ga.ga");
    registerPage.setUsername("agasshddsdssssfdfgagaga");
    registerPage.setPassword("12121212");
    registerPage.setPassword2("12121212");
    registerPage.setFirst_name("ga");
    registerPage.setLast_name("ga");
    registerPage.setAddress("ga");
    registerPage.setPhone_number("486484864");
    registerPage.getDate_birth().sendKeys("20");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("10");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("2010");
    System.out.println(registerPage.getDate_birth().getText());
    registerPage.getRegisterButton().click();
    browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    registerPage.getRegisterButton().click();
//    Alert alert = browser.switchTo().alert();
    new WebDriverWait(browser, 30).until(ExpectedConditions.alertIsPresent());
//    String text = alert.getText();
//    assertThat(text).isEqualTo("User successfully registered!");
//    alert.accept();
//    alert.dismiss();
    Thread.sleep(5000);
    browser.switchTo().alert().accept();
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/mainPage");
    browser.close();
  }

  @Test
  public void registerTest_User_ExistingCredentials() throws InterruptedException {
    registerPage.setEmail("agagagfassssddadfsga@ga.ga");
    registerPage.setUsername("agassdssssfdfgagaga");
    registerPage.setPassword("12121212");
    registerPage.setPassword2("12121212");
    registerPage.setFirst_name("ga");
    registerPage.setLast_name("ga");
    registerPage.setAddress("ga");
    registerPage.setPhone_number("486484864");
    registerPage.getDate_birth().sendKeys("20");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("10");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("2010");
    System.out.println(registerPage.getDate_birth().getText());
    registerPage.getRegisterButton().click();
    browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    registerPage.getRegisterButton().click();
//    Alert alert = browser.switchTo().alert();
//    String text = alert.getText();
//    assertThat(text).isEqualTo("User successfully registered!");
//    alert.accept();
//    alert.dismiss();
    Thread.sleep(5000);
//    browser.switchTo().alert().accept();
    WebElement error = browser.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-registration/mat-dialog-content/form/div[3]/div/div"));
    WebElement fail = browser.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-registration/mat-dialog-content/form/div[10]/div"));
    assertThat(fail).isNotNull();
    assertThat(error.getText()).isEqualTo("Fail -> Username is already taken!");
    browser.close();
  }

  @Test
  public void registerTest_User_ExistingEmail() throws InterruptedException {
    registerPage.setEmail("agagagfassssddadfsga@ga.ga");
    registerPage.setUsername("agassdssssfdfgasdsgaga");
    registerPage.setPassword("12121212");
    registerPage.setPassword2("12121212");
    registerPage.setFirst_name("ga");
    registerPage.setLast_name("ga");
    registerPage.setAddress("ga");
    registerPage.setPhone_number("486484864");
    registerPage.getDate_birth().sendKeys("20");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("10");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("2010");
    System.out.println(registerPage.getDate_birth().getText());
    registerPage.getRegisterButton().click();
    browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    registerPage.getRegisterButton().click();
//    Alert alert = browser.switchTo().alert();
    new WebDriverWait(browser, 30);
//    String text = alert.getText();
//    assertThat(text).isEqualTo("User successfully registered!");
//    alert.accept();
//    alert.dismiss();
    Thread.sleep(5000);
//    browser.switchTo().alert().accept();
    WebElement error = browser.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-registration/mat-dialog-content/form/div[3]/div/div"));
    WebElement fail = browser.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-registration/mat-dialog-content/form/div[10]/div"));
    assertThat(fail).isNotNull();
    assertThat(error.getText()).isEqualTo("Fail -> Email is already in use!");
    browser.close();
  }

  @Test
  public void registerTest_User_PasswordMismatch() throws InterruptedException {
    registerPage.setEmail("agagagfassssddadfsga@ga.ga");
    registerPage.setUsername("agassdssssfdfgasdsgaga");
    registerPage.setPassword("12121212");
    registerPage.setPassword2("121212212");
    registerPage.setFirst_name("ga");
    registerPage.setLast_name("ga");
    registerPage.setAddress("ga");
    registerPage.setPhone_number("486484864");
    registerPage.getDate_birth().sendKeys("20");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("10");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("2010");
    System.out.println(registerPage.getDate_birth().getText());
    browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    Alert alert = browser.switchTo().alert();
    new WebDriverWait(browser, 30);
//    String text = alert.getText();
//    assertThat(text).isEqualTo("User successfully registered!");
//    alert.accept();
//    alert.dismiss();
    Thread.sleep(5000);
//    browser.switchTo().alert().accept();
    WebElement error = browser.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-registration/mat-dialog-content/form/div[3]/div/div"));
    assertThat(error.getText()).isEqualTo("Passwords do not match!");
    browser.close();
  }

  @Test
  public void registerTest_User_ShortPassword() throws InterruptedException {
    registerPage.setEmail("agagagfassssddadfsga@ga.ga");
    registerPage.setUsername("agassdssssfdfgasdsgaga");
    registerPage.setPassword("121");
    registerPage.setPassword2("121");
    registerPage.setFirst_name("ga");
    registerPage.setLast_name("ga");
    registerPage.setAddress("ga");
    registerPage.setPhone_number("486484864");
    registerPage.getDate_birth().sendKeys("20");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("10");
    registerPage.getDate_birth().sendKeys(Keys.TAB);
    registerPage.getDate_birth().sendKeys("2010");
    System.out.println(registerPage.getDate_birth().getText());
    registerPage.getRegisterButton().click();
    browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    registerPage.getRegisterButton().click();
//    Alert alert = browser.switchTo().alert();
    new WebDriverWait(browser, 30);
//    String text = alert.getText();
//    assertThat(text).isEqualTo("User successfully registered!");
//    alert.accept();
//    alert.dismiss();
    Thread.sleep(5000);
//    browser.switchTo().alert().accept();
    WebElement error = browser.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-registration/mat-dialog-content/form/div[3]/div[2]/div"));
    assertThat(error.getText()).isEqualTo("Password must be at least 6 characters");
    browser.close();
  }

  @Test
  public void cancelTest() {
    registerPage.getCancelButton().click();
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/mainPage");
    browser.close();
  }


}
