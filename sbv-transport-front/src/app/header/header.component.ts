import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() featureSelected = new EventEmitter<string>();

  showView: string = 'notregister';

  constructor(private router: Router) { }

  ngOnInit() {
  }

  
  administratorView(){
    this.showView = 'administrator';
  }

  userView(){
    this.showView = 'user';
  }

  clickButtonLogin() {
    this.featureSelected.emit('login');
}

  clickButtonRegistration(){
    //this.featureSelected.emit('registration');
    this.router.navigateByUrl('/signup');
  }

  clickButtonBus(){
    this.featureSelected.emit('bus');
    
  }

  clickButtonSubway(){
    this.featureSelected.emit('subway');

  }

  clickButtonTrolley(){
    this.featureSelected.emit('trolley');
  }

  clickButtonController(){
    this.featureSelected.emit('controller');

  }

  clickButtonProfil(){
    this.featureSelected.emit('administrator');
  }
  
  clickButtonLine(){
    this.featureSelected.emit('line');
  }

  clickButtonStation(){
    this.featureSelected.emit('station');
  }

  clickButtonReport(){
    this.featureSelected.emit('report');
  }

  clickButtonHome(){
    this.featureSelected.emit('home');

  }

  clickButtonLogout(){
    this.featureSelected.emit('logout');

  }


}
