package com.sbvtransport.e2e.pages.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserSubwayPage {

  private WebDriver driver;

  @FindBy(css = "h1")
  private WebElement header;

  @FindBy(id = "map")
  private WebElement map;

  @FindBy(id = "filter_id_line")
  private WebElement linePicker;

  @FindBy(xpath = "//*[@id=\"main\"]/div[2]/table/tbody")
  private WebElement timetable;

  public UserSubwayPage(WebDriver driver) {
    this.driver = driver;
  }

  public void ensureIsDisplayed() {
    //wait for card number input field to be present
    (new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(
            By.id("map")));
  }

  public WebElement getHeader() {
    return header;
  }

  public WebElement getMap() {
    return map;
  }

  public WebElement getLinePicker() {
    return linePicker;
  }

  public void setLinePicker(String value) {
    WebElement el = getLinePicker();
    el.clear();
    el.sendKeys(value);
  }

  public WebElement getTimetable() {
    return timetable;
  }

}
