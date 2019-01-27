import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Timetable } from 'src/app/models/timetable.model';
import { StationService } from 'src/app/services/station.service';
import { Station } from 'src/app/models/station.model';

@Component({
  selector: 'app-line-timetable',
  templateUrl: './line-timetable.component.html',
  styleUrls: ['./line-timetable.component.css']
})
export class LineTimetableComponent implements OnInit {

  timetables: Timetable[] = [];
  firstStation: BigInteger;
  station: Station= new Station();
  show: String = "";

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, 
  private stationService:StationService) {
  }
  ngOnInit() {

    this.firstStation = this.data.firstStation;
    this.timetables = this.data.timetables;
    this.loadFirstStation();
    
  }

  loadFirstStation(){

    if(!(this.timetables.length == 0)){
      this.stationService.getStation(this.firstStation)
      .subscribe(data=>{
        this.station = data;
        this.showView();
      })
    }else{
      this.showView();
    }
    

  }

  showView(){

    if(this.timetables.length == 0){
      this.show = "not";
    }else{
      this.show ="exist";
    }

  }

  

}
