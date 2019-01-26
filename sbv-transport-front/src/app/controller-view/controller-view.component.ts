import { Component, OnInit, ViewChild, EventEmitter, Output } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ControllerService } from '../services/controller.service';
import { Controller } from '../models/controller.model';
import { Router } from '@angular/router';
import { TokenStorageService } from '../auth/token-storage.service';
import { TicketCheckComponent } from './ticket-check/ticket-check.component';
import { MatDialogConfig, MatDialog } from '@angular/material';

@Component({
  selector: 'app-controller-view',
  templateUrl: './controller-view.component.html',
  styleUrls: ['./controller-view.component.css']
})
export class ControllerViewComponent implements OnInit {

  @Output() featureSelected = new EventEmitter<string>();

  currentController: Controller;

  @ViewChild("header") header: HeaderComponent;
  showView: string = 'pricelist';

  constructor(private controllerService: ControllerService, public dialog: MatDialog, private router: Router, private token: TokenStorageService) { 
    
  }

  ngOnInit() {
    this.header.controllerView();
    this.controllerService.getController(this.token.getUsername()).subscribe((response) => {
      this.currentController = response;
    })
  }

  onNavigate(feature: string){
    this.showView = feature;
    if(feature == 'logout') {
      window.sessionStorage.clear();
      this.router.navigate(['mainPage']);
      window.alert("Successfully Logged out!");
    }
    if(feature == 'pricelist') {
      //
    }
  }

  checkTicket() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
    added: false
    };

    const dialogRef = this.dialog.open(TicketCheckComponent, dialogConfig);

    
  }

}
