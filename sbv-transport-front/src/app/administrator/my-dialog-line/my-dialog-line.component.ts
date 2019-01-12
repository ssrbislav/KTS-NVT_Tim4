import { Component, OnInit,Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Line } from 'src/app/models/line.model';
import { Station } from 'src/app/models/station.model';
import { StationService } from 'src/app/services/station.service';



@Component({
  selector: 'app-my-dialog',
  templateUrl: './my-dialog-line.component.html',
  styleUrls: ['./my-dialog-line.component.css']
})
export class MyDialogComponent implements OnInit {

  modalTitle: string;
  line: Line;
  fstation: string = "";
  s: Station;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private dialogRef: MatDialogRef<any>, private stationService: StationService) {
   this.modalTitle = data.title; 
   console.log(data)
   }
   
  ngOnInit() {
    this.line = this.data.line;
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadFirstStation();
  }

  loadFirstStation(){
    if(this.line.first_station != null){
      this.stationService.getStation(this.line.first_station)
      .subscribe( data => {
        this.s =  data; 
        this.fstation = this.s.location.location_name;
      });
    }
    
  }

}
