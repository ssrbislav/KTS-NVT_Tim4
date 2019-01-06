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

  clickButtonLogin() {
    this.featureSelected.emit('login');
}

  clickButtonRegistration(){
    this.router.navigateByUrl('/registration');
  }

  administratorView(){
    this.showView = 'administrator';
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
    this.featureSelected.emit('profil');
  }
  
  clickButtonLine(){
    this.featureSelected.emit('line');
  }

  clickButtonStation(){
    this.featureSelected.emit('station');
  }

  clickButtonLocation(){
    this.featureSelected.emit('location');
  }

  clickButtonReport(){
    this.featureSelected.emit('report');
  }


}
