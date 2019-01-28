package com.sbvtransport.e2e.pages.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

  private WebDriver driver;

  @FindBy(name = "email")
  private WebElement email;

  @FindBy(name = "username")
  private WebElement username;

  @FindBy(name = "password")
  private WebElement password;

  @FindBy(name = "password2")
  private WebElement password2;

  @FindBy(name = "first_name")
  private WebElement first_name;

  @FindBy(name = "last_name")
  private WebElement last_name;

  @FindBy(name = "address")
  private WebElement address;

  @FindBy(name = "phone_number")
  private WebElement phone_number;

  @FindBy(name = "date_birth")
  private WebElement date_birth;

  @FindBy(className = "btn")
  private WebElement registerButton;

  @FindBy(className = "cancelbtn")
  private WebElement cancelButton;

  public RegisterPage(WebDriver driver) {
    this.driver = driver;
  }

  public void ensureIsDisplayed() {
    //wait for card number input field to be present
    (new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(
            By.className("mat-dialog-container")));
  }

  public WebElement getEmail() {
    return email;
  }

  public void setEmail(String value) {
    WebElement el = getEmail();
    el.clear();
    el.sendKeys(value);
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

  public WebElement getPassword2() {
    return password2;
  }

  public void setPassword2(String value) {
    WebElement el = getPassword2();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String value) {
    WebElement el = getFirst_name();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getLast_name() {
    return last_name;
  }

  public void setLast_name(String value) {
    WebElement el = getLast_name();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getAddress() {
    return address;
  }

  public void setAddress(String value) {
    WebElement el = getAddress();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getPhone_number() {
    return phone_number;
  }

  public void setPhone_number(String value) {
    WebElement el = getPhone_number();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getDate_birth() {
    return date_birth;
  }

  public void setDate_birth(String value) {
    WebElement el = getDate_birth();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getRegisterButton() {
    return registerButton;
  }

  public WebElement getCancelButton() {
    return cancelButton;
  }
}
