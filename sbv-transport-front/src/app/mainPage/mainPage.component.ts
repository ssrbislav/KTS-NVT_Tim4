import { Component, OnInit ,ViewChild} from '@angular/core';
import { LoginComponent } from '../header/login/login.component';
import { RegistrationComponent } from '../header/registration/registration.component';

@Component({
  selector: 'app-main-page',
  templateUrl: './mainPage.component.html',
  styleUrls: ['./mainPage.component.css']
})
export class MainPageComponent implements OnInit {

  @ViewChild("login") login: LoginComponent;
  @ViewChild("registration") registration: RegistrationComponent;

  constructor(){

  }

  ngOnInit() {
  }

  showPopUp : string;

  onNavigate(feature: string){
    
    this.showPopUp = feature;
    console.log(feature);
    if(feature == 'login'){
      this.login.showLogin();
    }
  }
 

}
