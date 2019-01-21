import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  @ViewChild("header") header: HeaderComponent;
  showView: string = 'home';

  constructor() { }

  ngOnInit() {
    this.header.userView();
  }
  
  onNavigate(feature: string){
    console.log(feature);
    this.showView = feature;
    
  }

}
