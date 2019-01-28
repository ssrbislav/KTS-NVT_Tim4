package com.sbvtransport.e2e.tests.general;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbvtransport.e2e.pages.general.LoginPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

  private WebDriver browser;

  LoginPage loginPage;

  @Before
  public void setupSelenium() {
    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    browser = new ChromeDriver();
    browser.manage().window().maximize();
    browser.navigate().to("localhost:4200/mainPage");
    WebElement loginButton = browser.findElement(By.id("logInButton"));
    loginButton.click();
    loginPage = PageFactory.initElements(browser, LoginPage.class);
  }

  @Test
  public void loginTest_User_OK() {
    loginPage.setUsername("g");
    loginPage.setPassword("12121212");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.urlContains("user"));
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/user");
    browser.close();
  }

  @Test
  public void loginTest_User_BadUsernamePassword() {
    loginPage.setUsername("g");
    loginPage.setPassword("002211851");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/mainPage");
    browser.close();
  }

  @Test
  public void loginTest_Admin_OK() {
    loginPage.setUsername("admin");
    loginPage.setPassword("admin");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.urlContains("administrator"));
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/administrator");
    browser.close();
  }

  @Test
  public void loginTest_Admin_BadUsernamePassword() {
    loginPage.setUsername("admin");
    loginPage.setPassword("adminw22");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/mainPage");
    browser.close();
  }

  @Test
  public void loginTest_Controller_OK() {
    loginPage.setUsername("aaa");
    loginPage.setPassword("aaaaaa");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.urlContains("controller"));
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/controller");
    browser.close();
  }

  @Test
  public void loginTest_Controller_BadUsernamePassword() {
    loginPage.setUsername("aaa");
    loginPage.setPassword("adminw22");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/mainPage");
    browser.close();
  }

  @Test
  public void cancelTest() {
    loginPage.getCancelButton().click();
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/mainPage");
    browser.close();
  }

}
