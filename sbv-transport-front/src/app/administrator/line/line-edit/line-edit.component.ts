import { Component, OnInit, Inject } from '@angular/core';
import { Line } from 'src/app/models/line.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { CheckBoxItem } from 'src/app/models/checkBoxItem.model';
import { LineService } from 'src/app/services/line.service';
import { StationService } from 'src/app/services/station.service';
import { Station } from 'src/app/models/station.model';
import { AddStationToList } from 'src/app/models.dto/addStationToList.dto';

@Component({
  selector: 'app-line-edit',
  templateUrl: './line-edit.component.html',
  styleUrls: ['./line-edit.component.css']
})
export class LineEditComponent implements OnInit {

  line: Line;
  allIdStations: BigInteger[] = [];
  checkBoxItems: CheckBoxItem[] = [];
  allStations: Station[];
  addtoStations: Station[] = [];
  finalStations: AddStationToList[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private stationService: StationService, private lineService: LineService) { }

  ngOnInit() {
    this.line = this.data.line;
    this.loadAllStations();
  }

  loadAllStations(){
    this.stationService.getStations()
      .subscribe( data => {
        this.allStations = data;
        this.loadAllIdStations();
      });
  }

  loadAllIdStations(){
    for (var item of Array.from(this.line.station_list.values())) {
      this.allIdStations.push(item.id);
    }
    this.loadcheckBoxItems();
  }

  loadcheckBoxItems(){
    for(var i = 0; i < this.allStations.length; i++){
      
      if(this.allIdStations.includes(this.allStations[i].id)){
        var item = new CheckBoxItem(this.allStations[i],true);
        this.checkBoxItems.push(item);
      }else{
        var item = new CheckBoxItem(this.allStations[i],false);
        this.checkBoxItems.push(item);

      }
    }
  }

  editLine(){
    for (var i = 0; i < this.checkBoxItems.length; i++){
      if(this.checkBoxItems[i].checked){
        this.addtoStations.push(this.checkBoxItems[i].value);
      }
    }

    if(this.addtoStations.length != 0){
      for (var i = 0; i < this.addtoStations.length; i++){
        var s = new AddStationToList(this.addtoStations[i].id, this.line.id);
        this.finalStations.push(s);
      }
         
    }else{
      this.finalStations.push(new AddStationToList(null,this.line.id));
    }

    this.lineService.updateLine(this.finalStations)
        .subscribe( data => {
          alert( "Successfully line edited!");
          this.dialogRef.close();
          console.log(data);
      });  
  }

}
