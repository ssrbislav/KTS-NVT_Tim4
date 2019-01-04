import { Component,ViewChild} from '@angular/core';
import { LoginComponent } from './header/login/login.component';
import { RegistrationComponent } from './header/registration/registration.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  @ViewChild("login") login: LoginComponent;
  @ViewChild("registration") registration: RegistrationComponent;

  constructor(){

  }
  title = 'sbv-transport';

  showPopUp : string;

  onNavigate(feature: string){

    this.showPopUp = feature;
    console.log(feature);
    if(feature == 'login'){
      this.login.showLogin();
    }else{
      this.registration.showRegistration();
    }
  }


}



