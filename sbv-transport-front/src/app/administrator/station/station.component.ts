import { Component, OnInit ,ViewChild} from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { StationAddComponent } from './station-add/station-add.component';
import { StationTableComponent } from './station-table/station-table.component';

@Component({
  selector: 'app-station',
  templateUrl: './station.component.html',
  styleUrls: ['./station.component.css']
})
export class StationComponent implements OnInit {

  @ViewChild("appTable") table: StationTableComponent;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  addStation(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana",
      added: false
      };

    const dialogRef = this.dialog.open(StationAddComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.table.loadAllStations();

    });

  }

}
