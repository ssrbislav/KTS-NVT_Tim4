import { browser, by, element } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/mainPage');
  }

  // getTitleText() {
  //   return element(by.id('title')).getText();
  // }
}
