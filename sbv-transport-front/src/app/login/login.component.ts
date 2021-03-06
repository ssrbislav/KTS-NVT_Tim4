import { Component, OnInit ,Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { AuthLoginInfo } from '../auth/login-info';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  // @ViewChild('usernameInput') usernameInputRef: ElementRef;
  // @ViewChild('passwordInput') passwordInputRef: ElementRef;
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private dialogRef: MatDialogRef<any>, private authService: AuthService, private tokenStorage: TokenStorageService,
            private router: Router) {}

  ngOnInit() {
    if(this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  onSubmit() {

    this.loginInfo = new AuthLoginInfo(
      this.form.username,
      this.form.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.roles = this.tokenStorage.getAuthorities();
        this.dialogRef.close();
        this.roles.every(role => {
          if(role === 'ROLE_ADMIN') {
            this.router.navigate(['administrator']); 
            return true;
          } else if(role == 'ROLE_CONTROLLER') {
            this.router.navigate(['controller']);
            return true;
          } else {
            this.router.navigate(['user']);
            return true;
          }
        });
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    )

  }

  reloadPage() {
    window.location.reload();
  }

}
