package com.sbvtransport.e2e.pages.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserBuyTicketPage {

  private WebDriver driver;



  @FindBy(id = "type_transport")
  private WebElement transportType;

  @FindBy(id = "zone")
  private WebElement zone;

  @FindBy(id = "ticket_type")
  private WebElement ticketType;

  @FindBy(id = "demographic_type")
  private WebElement demoType;

  @FindBy(name = "code_transport")
  private WebElement code;

  @FindBy(name = "date_birth")
  private WebElement valid;

  @FindBy(className = "btn")
  private WebElement addButton;

  public void ensureIsDisplayed() {
    //wait for card number input field to be present
    (new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(
            By.className("thumbnail")));
  }

  public UserBuyTicketPage(WebDriver driver) {
    this.driver = driver;
  }

  public WebElement getTransportType() {
    return transportType;
  }

  public void setTransportType(String value) {
    WebElement el = getTransportType();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getZone() {
    return zone;
  }

  public void setZone(String value) {
    WebElement el = getZone();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getTicketType() {
    return ticketType;
  }

  public void setTicketType(String value) {
    WebElement el = getTicketType();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getDemoType() {
    return demoType;
  }

  public void setDemoType(String value) {
    WebElement el = getDemoType();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getCode() {
    return code;
  }

  public void setCode(String value) {
    WebElement el = getCode();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getValid() {
    return valid;
  }

  public void setValid(String value) {
    WebElement el = getValid();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getAddButton() {
    return addButton;
  }

}
