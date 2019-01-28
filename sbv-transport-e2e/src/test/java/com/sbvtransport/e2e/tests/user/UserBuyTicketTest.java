package com.sbvtransport.e2e.tests.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbvtransport.e2e.pages.general.LoginPage;
import com.sbvtransport.e2e.pages.user.UserBuyTicketPage;
import com.sbvtransport.e2e.pages.user.UserPage;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserBuyTicketTest {

  private WebDriver browser;

  UserPage userPage;
  LoginPage loginPage;
  UserBuyTicketPage userBuyTicketPage;

  @Before
  public void setupSelenium() {
    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    browser = new ChromeDriver();
    browser.manage().window().maximize();
    browser.navigate().to("localhost:4200/mainPage");
    WebElement loginButton = browser.findElement(By.id("logInButton"));
    loginButton.click();
    loginPage = PageFactory.initElements(browser, LoginPage.class);
    login();
    userPage = PageFactory.initElements(browser, UserPage.class);
    WebElement buyButton = userPage.getBuyTickets();
    buyButton.click();
    userBuyTicketPage = PageFactory.initElements(browser, UserBuyTicketPage.class);
  }

  public void login() {
    loginPage.setUsername("pass");
    loginPage.setPassword("passenger");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.urlContains("user"));
  }

  @Test
  public void testBuyTicket() throws InterruptedException {
    userBuyTicketPage.setCode("bla");
    userBuyTicketPage.getDemoType().click();
    userBuyTicketPage.getDemoType().sendKeys("standard");
    userBuyTicketPage.getTicketType().click();
    userBuyTicketPage.getTicketType().sendKeys("oneUse");
    userBuyTicketPage.getTransportType().click();
    userBuyTicketPage.getTransportType().sendKeys("bus");
    userBuyTicketPage.getZone().click();
    userBuyTicketPage.getZone().sendKeys("first");
    userBuyTicketPage.getValid().click();
    userBuyTicketPage.getValid().sendKeys("20");
    userBuyTicketPage.getValid().sendKeys(Keys.TAB);
    Thread.sleep(5000);
    browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    userBuyTicketPage.getValid().sendKeys("Feb");
    userBuyTicketPage.getValid().sendKeys(Keys.ARROW_UP);
    userBuyTicketPage.getValid().sendKeys(Keys.TAB);
    browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    Thread.sleep(5000);
    userBuyTicketPage.getValid().sendKeys("2019");
    userBuyTicketPage.getValid().sendKeys(Keys.ARROW_UP);
    browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    Thread.sleep(5000);
    userBuyTicketPage.getValid().sendKeys(Keys.TAB);
    browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    userBuyTicketPage.getAddButton().click();
    new WebDriverWait(browser, 50).until(ExpectedConditions.alertIsPresent());
    Thread.sleep(5000);
    assertThat(browser.switchTo().alert().getText()).isEqualTo("Ticket successfully bought!");
    browser.switchTo().alert().accept();
    Thread.sleep(5000);
    assertThat(browser.getCurrentUrl()).isEqualTo("http://localhost:4200/user");
    browser.close();
  }

}
