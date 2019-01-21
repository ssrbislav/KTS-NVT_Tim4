import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-controller-view',
  templateUrl: './controller-view.component.html',
  styleUrls: ['./controller-view.component.css']
})
export class ControllerViewComponent implements OnInit {

  @ViewChild("header") header: HeaderComponent;

  constructor() { }

  ngOnInit() {
    this.header.controllerView();
  }

}
