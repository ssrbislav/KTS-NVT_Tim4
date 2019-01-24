import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  private roles: string[];

  @Output() featureSelected = new EventEmitter<string>();

  showView: string = 'notregister';

  constructor(private router: Router, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if(this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
        this.roles.every(role => {
          if(role === 'ROLE_ADMIN') {
            this.router.navigate(['administrator']); 
            return true;
          } else if(role == 'ROLE_CONTROLLER') {
            this.router.navigate(['controller']);
            return true;
          } else if(role == 'ROLE_PASSENGER'){
            this.router.navigate(['user']);
            return true;
          }
        });
    }
  }

  
  administratorView(){
    this.showView = 'administrator';
  }

  controllerView(){
    this.showView = 'controllerView';
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
    this.featureSelected.emit('profil');
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
