import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Station } from 'src/app/models/station.model';


@Component({
  selector: 'app-my-dialog-first-station',
  templateUrl: './my-dialog-first-station.component.html',
  styleUrls: ['./my-dialog-first-station.component.css']
})
export class MyDialogFirstStationComponent implements OnInit {

  station: Station;
  show :string = 'exist';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>) { 
    
  }

  ngOnInit() {
    this.station = this.data.station;
    this.loadPage();
    
  }

  loadPage(){
    if(this.station == null){
      this.show = 'not';
    }
  }

}
