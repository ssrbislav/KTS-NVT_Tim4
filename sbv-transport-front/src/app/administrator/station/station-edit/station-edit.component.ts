import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Station } from 'src/app/models/station.model';
import { changeStationDTO } from 'src/app/models.dto/changeStation.dto';
import { StationService } from 'src/app/services/station.service';

@Component({
  selector: 'app-station-edit',
  templateUrl: './station-edit.component.html',
  styleUrls: ['./station-edit.component.css']
})
export class StationEditComponent implements OnInit {

  station: Station;
  changeStation: changeStationDTO = new changeStationDTO();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, 
  private stationService: StationService) { }

  ngOnInit() {
    this.station = this.data.station;
  }

  editStation(){
    this.changeStation.id_station = this.station.id;
    this.changeStation.location_name = this.station.location.location_name;
    this.changeStation.zone = this.station.zone;

    this.stationService.updateStation(this.changeStation)
        .subscribe(data =>{
          if(data != null){
            alert("Successfully station edit!");
            this.dialogRef.close();
          }else{
            alert("Something went wrong!");
          }

        });
  }

}
