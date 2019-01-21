import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  @ViewChild("header") header: HeaderComponent;
  showView: string = 'home';

  constructor(private router: Router) { }

  ngOnInit() {
    this.header.userView();
  }
  
  onNavigate(feature: string){
    console.log(feature);
    this.showView = feature;
    if(feature == 'logout') {
      window.sessionStorage.clear();
      this.router.navigate(['mainPage']);
      window.alert("User successfully Logged out!");
    }
  }

  busView(){
    this.showView = 'bus';
  }
  subwayView() {
    this.showView = 'subway';
  }
  trolleyView() {
    this.showView = 'trolley';
  }

}
