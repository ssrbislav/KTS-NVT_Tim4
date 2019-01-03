import { Component, OnInit ,Input} from '@angular/core';
import {Popup} from 'ng2-opd-popup';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private username: string;
  private password: string;

  @Input() show: boolean = false;

  constructor(private popup : Popup) {
  }

  ngOnInit() {
    
  }

  showLogin() {
    this.popup.show();
  }

}
