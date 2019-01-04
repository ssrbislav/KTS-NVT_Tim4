import { Component, OnInit } from '@angular/core';
import { Popup } from 'ng2-opd-popup';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private popup2: Popup) { }

  ngOnInit() {
  }

  showRegistration() {
    this.popup2.options = {
      header: "Registration",
      confirmBtnContent: "Registration"
      };
    this.popup2.show(this.popup2.options);
  }

}
