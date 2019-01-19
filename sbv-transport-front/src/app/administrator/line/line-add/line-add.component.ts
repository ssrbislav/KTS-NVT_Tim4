import { Component, OnInit ,Inject} from '@angular/core';
import { LineDTO } from 'src/app/models.dto/line.dto';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Station } from 'src/app/models/station.model';
import { StationService } from 'src/app/services/station.service';
import { CheckBoxItem } from 'src/app/models/checkBoxItem.model';
import { LineService } from 'src/app/services/line.service';
import { AddStationToList } from 'src/app/models.dto/addStationToList.dto';
import { Line } from 'src/app/models/line.model';

@Component({
  selector: 'app-line-add',
  templateUrl: './line-add.component.html',
  styleUrls: ['./line-add.component.css']
})
export class LineAddComponent implements OnInit {

  line: LineDTO = new LineDTO();
  show: string = 'line';
  stations: Station[];
  addtoStations: Station[] = [];
  checkBoxItems:  CheckBoxItem[] = [];
  newLine: Line;
  finalStations: AddStationToList[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, 
  private stationService: StationService, private lineService: LineService) { }

  ngOnInit() {
  }

  loadAllStations(){
    this.stationService.getStations()
      .subscribe( data => {
        this.stations = data;
        this.loadCheckItems();
       
      });
  }

  loadCheckItems(){

    for (var i = 0; i < this.stations.length; i++){
      this.checkBoxItems.push(new CheckBoxItem(this.stations[i],false));
    }
    
  }
  nextClick(){

    if(this.line.name == null){
      alert("Please write line name!");
    }else if(this.line.zone == null){
      alert("Please select zone!");
    }else if(this.line.line_type == null){
      alert("Please select line type!");
    }else{
      this.loadAllStations();
      this.show = 'station';
    } 
  }

  backClick(){
    this.show = 'line';
  }

  addLine(){

    for (var i = 0; i < this.checkBoxItems.length; i++){
      if(this.checkBoxItems[i].checked){
        this.addtoStations.push(this.checkBoxItems[i].value);
      }
    }

    this.lineService.addLine(this.line)
    .subscribe( data => {
      if(data!= null){
        this.newLine = data;
        this.addStations();

      }else{
        alert("Something went wrong!");
      }
     
    });
    
  }

  addStations(){

    if(this.addtoStations.length != 0){
      for (var i = 0; i < this.addtoStations.length; i++){
        var s = new AddStationToList(this.addtoStations[i].id, this.newLine.id);
        this.finalStations.push(s);
      }
      this.addStationToList();
     
    }

  }

  addStationToList(){
    this.lineService.addStation(this.finalStations)
        .subscribe( data => {
          alert( "Successfully line added!");
          this.dialogRef.close();
          console.log(data);
      });
  }

}
