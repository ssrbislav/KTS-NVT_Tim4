import { Component, OnInit ,Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Station } from 'src/app/models/station.model';


@Component({
  selector: 'app-my-dialog-stations',
  templateUrl: './my-dialog-stations.component.html',
  styleUrls: ['./my-dialog-stations.component.css']
})
export class MyDialogStationsComponent implements OnInit {

  stations: Station[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>) { }

  ngOnInit() {
    this.stations = this.data.station_list;
  }

}
