import { Component, OnInit,Output, EventEmitter, Input } from '@angular/core';
import { Bus } from 'src/app/models/bus.model';
import { BusService } from 'src/app/services/bus.service';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { Line } from 'src/app/models/line.model';
import {MyDialogComponent} from 'src/app/administrator/my-dialog-line/my-dialog-line.component'
import { MyDialogCurrentLocationComponent } from '../../my-dialog-current-location/my-dialog-current-location.component';
import { BusEditComponent } from '../bus-edit/bus-edit.component';
import { Timetable } from 'src/app/models/timetable.model';
import { MyDialogTimetableComponent } from '../../my-dialog-timetable/my-dialog-timetable.component';


@Component({
  selector: 'app-bus-table',
  templateUrl: './bus-table.component.html',
  styleUrls: ['./bus-table.component.css']
})
export class BusTableComponent implements OnInit {

  buses: Bus[];
  @Output() deleted = new EventEmitter<boolean>();
  @Input() busSearch: any;

  constructor(private busService:BusService, public dialog: MatDialog) { }

  ngOnInit() { 
    this.loadAllBuses();     
  }
  
  loadAllBuses(){
    this.busService.getBuses()
      .subscribe( data => {
        this.buses = data;
         
      });
     
  }

  deleteBus(id: BigInteger){
    console.log(id);
    this.busService.deleteBus(id)
      .subscribe( data => {
        if(data == true){
          alert("Bus is deleted!");
          this.loadAllBuses();
          this.deleted.emit(true);
          this.busSearch.resetSearch();  
 
        }else{
          alert("Something went wrong!");
        }
      });
  }

  openModalLine(line: Line) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana",
      line: line
      };

    const dialogRef = this.dialog.open(MyDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)

    });
 }

 openModalLocation(location: Location) {
  const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      location: location
      };

    const dialogRef = this.dialog.open(MyDialogCurrentLocationComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)

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

  editBus(bus : Bus){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      bus: bus
      };

    const dialogRef = this.dialog.open(BusEditComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.loadAllBuses();
    this.deleted.emit(true);
    this.busSearch.resetSearch(); 

    });

  }

  loadSearchFilter(filterBuses: Bus[]){
    this.buses = filterBuses;
  }

}
