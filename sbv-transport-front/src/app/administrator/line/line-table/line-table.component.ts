import { Component, OnInit, Input } from '@angular/core';
import { LineService } from 'src/app/services/line.service';
import { Line } from 'src/app/models/line.model';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { MyDialogFirstStationComponent } from './my-dialog-first-station/my-dialog-first-station.component';
import { Station } from 'src/app/models/station.model';
import { StationService } from 'src/app/services/station.service';
import { MyDialogStationsComponent } from './my-dialog-stations/my-dialog-stations.component';
import { LineEditComponent } from '../line-edit/line-edit.component';
import { Timetable } from 'src/app/models/timetable.model';
import { MyDialogTimetableComponent } from '../../my-dialog-timetable/my-dialog-timetable.component';

@Component({
  selector: 'app-line-table',
  templateUrl: './line-table.component.html',
  styleUrls: ['./line-table.component.css']
})
export class LineTableComponent implements OnInit {

  lines: Line[];
  firstStation: Station;
  @Input() lineSearch: any;

  constructor(private lineService: LineService, private stationService: StationService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadAllLines();
  }

  loadAllLines(){
    this.lineService.getLines()
      .subscribe( data => {
        this.lines = data;
      });
  }

  deleteLine(id: BigInteger){
    console.log(id);
    this.lineService.deleteLine(id)
      .subscribe( data => {
        if(data == true){
          alert("Line is deleted!");
          this.loadAllLines();
          this.lineSearch.resetSearch(); 

        }else{
          alert("Something went wrong!");
        }
      });
  }

  findFirstStation(id: BigInteger){
    if(id != null){
      this.stationService.getStation(id)
        .subscribe( data => {
        this.firstStation = data;
        this.openFirstStation();
        });
    }else{
      this.openFirstStation();
    }

  }

  openFirstStation(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: 1,
      station: this.firstStation
    };

    const dialogRef = this.dialog.open(MyDialogFirstStationComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      this.firstStation = null;
      console.log("Dialog was closed")
      console.log(result)

  });

  }

  openStations(station_list: Set<Station>){
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: 1,
      station_list: station_list
    };

    const dialogRef = this.dialog.open(MyDialogStationsComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)

  });

  }

  editLine(line : Line){

    const dialogConfig = new MatDialogConfig();
  
    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      line: line
      };
  
    const dialogRef = this.dialog.open(LineEditComponent, dialogConfig);
  
    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.loadAllLines();
    this.lineSearch.resetSearch(); 
  
    });
  
  }

  openModalTimetable(timetable: Timetable){
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      timetable: timetable
      };

    const dialogRef = this.dialog.open(MyDialogTimetableComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)

    });
  }

  loadSearchFilter(filterBuses: Line[]){
    this.lines= filterBuses;
  }

  

}
