import { Component, OnInit, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { SignUpInfo } from '../auth/signup-info';
import { AuthService } from '../auth/auth.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  signUpInfo: SignUpInfo = new SignUpInfo();
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
  passwordRepeat: string;

  constructor( public dialogRef: MatDialogRef<any>, private router: Router, private authService: AuthService) { }

  ngOnInit() {
    this.dialogRef.updateSize('50%', '80%');
  }

  cancelClicked(){
    this.dialogRef.close();
    //this.router.navigateByUrl('/');
  }

  checkSame(pass: string) {
    this.passwordRepeat = pass;
    if (this.passwordRepeat !== this.signUpInfo.password) {
      this.errorMessage = "Passwords do not match!"
    }
    else {
      this.errorMessage = "";
    }
  }

  onSubmit() {
    this.signUpInfo.role = ['user'];
    this.authService.signUp(this.signUpInfo).subscribe(
      data => {
        window.alert("User successfully registered!");
        this.isSignUpFailed = false;
        this.isSignedUp = true;
        this.dialogRef.close();
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );

  }
}
