import { Component, OnInit } from '@angular/core';
import { StationService } from 'src/app/services/station.service';
import { Station } from 'src/app/models/station.model';

@Component({
  selector: 'app-station-table',
  templateUrl: './station-table.component.html',
  styleUrls: ['./station-table.component.css']
})
export class StationTableComponent implements OnInit {

  stations: Station[];

  constructor(private stationService:StationService) { }

  ngOnInit() {
    this.loadAllStations();
  }

  loadAllStations(){
    this.stationService.getStations()
      .subscribe( data => {
        this.stations = data;
      });
  }

  deleteStation(id: BigInteger){
    console.log(id);
    this.stationService.deleteStation(id)
      .subscribe( data => {
        if(data == true){
          alert("Station is deleted!");
          this.loadAllStations();
        }else{
          alert("Something went wrong!");
        }
      });
  }

}
