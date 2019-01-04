import { Component,ViewChild} from '@angular/core';
import { LoginComponent } from './header/login/login.component';
import { RegistrationComponent } from './header/registration/registration.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'sbv-transport';

  constructor() { }

}



