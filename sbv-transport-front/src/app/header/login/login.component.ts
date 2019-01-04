import { Component, OnInit ,Input,ViewChild,ElementRef} from '@angular/core';
import {Popup} from 'ng2-opd-popup';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('usernameInput') usernameInputRef: ElementRef;
  @ViewChild('passwordInput') passwordInputRef: ElementRef;


  @Input() show: boolean = false;

  constructor(private popup : Popup) {
  }

  ngOnInit() {
    
  }

  showLogin() {
    this.popup.options = {
      header: "Login",
      confirmBtnContent: "Login"
      };
    this.popup.show(this.popup.options);
  }

  logIn(){
    const username = this.usernameInputRef.nativeElement.value;
    const password = this.passwordInputRef.nativeElement.value;
    console.log("username je " + username);
    console.log("password je:" + password);

    this.popup.hide();
  }

}
