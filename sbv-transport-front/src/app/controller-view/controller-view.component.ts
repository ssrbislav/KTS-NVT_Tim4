import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ControllerService } from '../services/controller.service';
import { Controller } from '../models/controller.model';
import { Router } from '@angular/router';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-controller-view',
  templateUrl: './controller-view.component.html',
  styleUrls: ['./controller-view.component.css']
})
export class ControllerViewComponent implements OnInit {

  currentController: Controller;

  @ViewChild("header") header: HeaderComponent;
  showView: string = 'home';

  constructor(private controllerService: ControllerService, private router: Router, private token: TokenStorageService) { 
    this.controllerService.getController(token.getUsername()).subscribe((response) => {
      this.currentController = response;
    })
  }

  ngOnInit() {
    this.header.controllerView();
  }

  onNavigate(feature: string){
    this.showView = feature;
    if(feature == 'logout') {
      window.sessionStorage.clear();
      this.router.navigate(['mainPage']);
      window.alert("Successfully Logged out!");
    }
    if(feature == 'profil') {
      alert(this.currentController.address);
    }
  }

}
