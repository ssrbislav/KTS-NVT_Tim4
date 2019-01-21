import { Component,ViewChild} from '@angular/core';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { TokenStorageService } from './auth/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'sbv-transport';

  private roles: string[];
  private authority: string;

  constructor(private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if(this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if(role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if(role == 'ROLE_CONTROLLER') {
          this.authority = 'controller';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
    
  }

}



