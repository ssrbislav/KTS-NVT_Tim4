import { Component, OnInit,Output, EventEmitter, Input } from '@angular/core';
import { Subway } from 'src/app/models/subway.model';
import { SubwayService } from 'src/app/services/subway.service';
import { Line } from 'src/app/models/line.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { MyDialogComponent } from '../../my-dialog-line/my-dialog-line.component';
import { MyDialogCurrentLocationComponent } from '../../my-dialog-current-location/my-dialog-current-location.component';
import { SubwayEditComponent } from '../subway-edit/subway-edit.component';
import { Timetable } from 'src/app/models/timetable.model';
import { MyDialogTimetableComponent } from '../../my-dialog-timetable/my-dialog-timetable.component';

@Component({
  selector: 'app-subway-table',
  templateUrl: './subway-table.component.html',
  styleUrls: ['./subway-table.component.css']
})
export class SubwayTableComponent implements OnInit {
  
  subways: Subway[];
  @Output() deleted = new EventEmitter<boolean>();
  @Input() subwaySearch: any;

  constructor(private subwayService: SubwayService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadAllSubways();
  }

  loadAllSubways(){
    this.subwayService.getSubways()
      .subscribe( data => {
        this.subways = data;
      });
  }

  deleteSubway(id: BigInteger){
    console.log(id);
    this.subwayService.deleteSubway(id)
      .subscribe( data => {
        if(data == true){
          alert("Subway is deleted!");
          this.loadAllSubways();
          this.deleted.emit(true);
          this.subwaySearch.resetSearch();  
 
        }else{
          alert("Something went wrong!");
        }
      });
  }

  openModal(line: Line) {
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

    editSubway(subway : Subway){

      const dialogConfig = new MatDialogConfig();
    
      dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.data = {
        id: 1,
        subway:subway
        };
    
      const dialogRef = this.dialog.open(SubwayEditComponent, dialogConfig);
    
      dialogRef.afterClosed().subscribe(result => {
      console.log("Dialog was closed")
      console.log(result)
      this.loadAllSubways();
      this.deleted.emit(true);
      this.subwaySearch.resetSearch();  
    
      });
    
    }

    loadSearchFilter(filterSubways:Subway[]){
      this.subways = filterSubways;
    }

}
