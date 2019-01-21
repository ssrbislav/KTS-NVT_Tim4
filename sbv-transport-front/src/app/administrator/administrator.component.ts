import { Component, OnInit,ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css']
})
export class AdministratorComponent implements OnInit {

  @ViewChild("header") header: HeaderComponent;
  showView: string = 'bus';

  constructor(private router: Router) { 
  }

  ngOnInit() {
    this.header.administratorView();
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

}
