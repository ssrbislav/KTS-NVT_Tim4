import { Component, OnInit } from '@angular/core';
import { Popup } from 'ng2-opd-popup';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  cancelClicked(){

    this.router.navigateByUrl('/');
  }
  

}
