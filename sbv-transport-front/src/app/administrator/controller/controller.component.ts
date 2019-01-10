import { Component, OnInit,ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { ControllerAddComponent } from './controller-add/controller-add.component';
import { ControllerTableComponent } from './controller-table/controller-table.component';

@Component({
  selector: 'app-controller',
  templateUrl: './controller.component.html',
  styleUrls: ['./controller.component.css']
})
export class ControllerComponent implements OnInit {

  @ViewChild("appTable") table: ControllerTableComponent;

  constructor(private router:Router,public dialog: MatDialog) { }

  ngOnInit() {
  }

  addController(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana",
      added: false
      };

    const dialogRef = this.dialog.open(ControllerAddComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.table.loadAllControllers();

    });

    

  }

}
