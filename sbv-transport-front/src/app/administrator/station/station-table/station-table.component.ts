import { Component, OnInit ,Output,EventEmitter, Input} from '@angular/core';
import { StationService } from 'src/app/services/station.service';
import { Station } from 'src/app/models/station.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { StationEditComponent } from '../station-edit/station-edit.component';

@Component({
  selector: 'app-station-table',
  templateUrl: './station-table.component.html',
  styleUrls: ['./station-table.component.css']
})
export class StationTableComponent implements OnInit {

  stations: Station[];
  @Output() deleted = new EventEmitter<boolean>();
  @Input() stationSearch: any;

  constructor(private stationService:StationService, public dialog: MatDialog) { }

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
          this.deleted.emit(true);
          this.stationSearch.resetSearch();  
 
        }else{
          alert("Something went wrong!");
        }
      });
  }

  editStation(station : Station){

    const dialogConfig = new MatDialogConfig();
  
    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      station:station
      };
  
    const dialogRef = this.dialog.open(StationEditComponent, dialogConfig);
  
    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.loadAllStations();
    this.stationSearch.resetSearch();  

  
    });
  
  }

  loadSearchFilter(filterBuses: Station[]){
    this.stations = filterBuses;
  }


}
