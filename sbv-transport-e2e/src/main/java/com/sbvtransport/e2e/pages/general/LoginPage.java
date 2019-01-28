package com.sbvtransport.e2e.pages.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

  private WebDriver driver;

  @FindBy(name = "username")
  private WebElement username;

  @FindBy(name = "password")
  private WebElement password;

  @FindBy(className = "btn")
  private WebElement loginButton;

  @FindBy(className = "cancelbtn")
  private WebElement cancelButton;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
  }

  public void ensureIsDisplayed() {
    //wait for card number input field to be present
    (new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(
            By.className("mat-dialog-container")));
  }

  public WebElement getUsername() {
    return username;
  }

  public void setUsername(String value) {
    WebElement el = getUsername();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getPassword() {
    return password;
  }

  public void setPassword(String value) {
    WebElement el = getPassword();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getLoginButton() {
    return loginButton;
  }

  public WebElement getCancelButton() {
    return cancelButton;
  }
}
