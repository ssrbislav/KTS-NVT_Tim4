import { Component, OnInit,ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css']
})
export class AdministratorComponent implements OnInit {

  @ViewChild("header") header: HeaderComponent;

  constructor() { 
  }

  ngOnInit() {
    this.header.administratorView();

  }

}
