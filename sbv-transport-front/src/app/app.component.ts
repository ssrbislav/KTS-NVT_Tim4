import { Component,ViewChild} from '@angular/core';
import { LoginComponent } from './header/login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  @ViewChild("login") login: LoginComponent;

  constructor(){

  }
  title = 'sbv-transport';

  showPopUp : string;

  onNavigate(feature: string){

    this.showPopUp = feature;

    if(feature == 'login') 
        this.login.showLogin();
  }


}



