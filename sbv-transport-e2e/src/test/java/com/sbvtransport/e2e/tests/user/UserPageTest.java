package com.sbvtransport.e2e.tests.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbvtransport.e2e.pages.general.LoginPage;
import com.sbvtransport.e2e.pages.user.UserPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPageTest {

  private WebDriver browser;

  UserPage userPage;
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
    login();
    userPage = PageFactory.initElements(browser, UserPage.class);
  }

  public void login() {
    loginPage.setUsername("g");
    loginPage.setPassword("12121212");
    loginPage.getLoginButton().click();
    new WebDriverWait(browser, 15).until(ExpectedConditions.urlContains("user"));
  }

  @Test
  public void logout() {

  }

  @Test
  public void clickHomeHeaderTest() {
    WebElement tickets = browser.findElement(By.xpath("/html/body/app-root/app-user/app-header/nav/div/div[2]/ul[1]/ul/li[1]/a"));
    tickets.click();
    assertThat(browser.findElements(By.className("table"))).isNotNull();
    browser.close();
  }

  @Test
  public void clickBusTest() {
    userPage.getBus().click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Buses");
    browser.close();
  }

  @Test
  public void clickBusHeaderTest() {
    WebElement tickets = browser.findElement(By.xpath("/html/body/app-root/app-user/app-header/nav/div/div[2]/ul[1]/ul/li[2]/a"));
    tickets.click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Buses");
    browser.close();
  }

  @Test
  public void clickSubwayTest() {
    userPage.getSubway().click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Subways");
    browser.close();
  }

  @Test
  public void clickSubwayHeaderTest() {
    WebElement tickets = browser.findElement(By.xpath("/html/body/app-root/app-user/app-header/nav/div/div[2]/ul[1]/ul/li[3]/a"));
    tickets.click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Subways");
    browser.close();
  }

  @Test
  public void clickTrolleyTest() {
    userPage.getTrolley().click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Trolleys");
    browser.close();
  }

  @Test
  public void clickTrolleyHeaderTest() {
    WebElement tickets = browser.findElement(By.xpath("/html/body/app-root/app-user/app-header/nav/div/div[2]/ul[1]/ul/li[4]/a"));
    tickets.click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Trolleys");
    browser.close();
  }

  @Test
  public void clickBuyTicketTest() {
    userPage.getBuyTickets().click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Buy a ticket");
    browser.close();
  }

  @Test
  public void clickBuyTicketsHeaderTest() {
    WebElement tickets = browser.findElement(By.xpath("/html/body/app-root/app-user/app-header/nav/div/div[2]/ul[1]/ul/li[5]/a"));
    tickets.click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Buy a ticket");
    browser.close();
  }

  @Test
  public void clickTicketsTest() {
    userPage.getYourTickets().click();
    assertThat(browser.findElements(By.className("table"))).isNotNull();
    browser.close();
  }

  @Test
  public void clickTicketsHeaderTest() {
    WebElement tickets = browser.findElement(By.xpath("/html/body/app-root/app-user/app-header/nav/div/div[2]/ul[2]/li[1]/a"));
    tickets.click();
    assertThat(browser.findElements(By.className("table"))).isNotNull();
    browser.close();
  }

  @Test
  public void clickProfileTest() {
    userPage.getProfile().click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Profil");
    browser.close();
  }

  @Test
  public void clickProfileHeaderTest() {
    WebElement tickets = browser.findElement(By.xpath("/html/body/app-root/app-user/app-header/nav/div/div[2]/ul[2]/li[2]/a"));
    tickets.click();
    assertThat(browser.findElement(By.cssSelector("h1")).getText()).isEqualTo("Profil");
    browser.close();
  }

}
