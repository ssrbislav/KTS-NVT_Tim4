package com.sbvtransport.e2e.pages.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPage {

  private WebDriver driver;

  @FindBy(xpath = "/html/body/app-root/app-user/div/div[1]/div[1]/div")
  private WebElement bus;

  @FindBy(xpath = "/html/body/app-root/app-user/div/div[1]/div[2]/div")
  private WebElement subway;

  @FindBy(xpath = "/html/body/app-root/app-user/div/div[1]/div[3]/div")
  private WebElement trolley;

  @FindBy(xpath = "/html/body/app-root/app-user/div/div[3]/div")
  private WebElement buyTickets;

  @FindBy(xpath = "/html/body/app-root/app-user/div/div[4]/div")
  private WebElement yourTickets;

  @FindBy(xpath = "/html/body/app-root/app-user/div/div[5]/div")
  private WebElement profile;

  public void ensureIsDisplayed() {
    //wait for card number input field to be present
    (new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(
            By.className("thumbnail")));
  }

  public UserPage(WebDriver driver) {
    this.driver = driver;
  }

  public WebElement getBus() {
    return bus;
  }

  public WebElement getSubway() {
    return subway;
  }

  public WebElement getTrolley() {
    return trolley;
  }

  public WebElement getBuyTickets() {
    return buyTickets;
  }

  public WebElement getYourTickets() {
    return yourTickets;
  }

  public WebElement getProfile() {
    return profile;
  }
}
