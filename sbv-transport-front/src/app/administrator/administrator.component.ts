import { Component, OnInit,ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css']
})
export class AdministratorComponent implements OnInit {

  @ViewChild("header") header: HeaderComponent;
  showView: string = 'bus';

  constructor() { 
  }

  ngOnInit() {
    this.header.administratorView();
  }

  // busView(){
  //   this.showView = 'bus';
  // }

  // subwayView(){
  //   this.showView = 'subway';
  // }

  onNavigate(feature: string){
    console.log(feature);
    this.showView = feature;
    
  }

}
