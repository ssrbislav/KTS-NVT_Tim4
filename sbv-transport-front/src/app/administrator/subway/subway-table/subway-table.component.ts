import { Component, OnInit } from '@angular/core';
import { Subway } from 'src/app/models/subway.model';
import { SubwayService } from 'src/app/services/subway.service';

@Component({
  selector: 'app-subway-table',
  templateUrl: './subway-table.component.html',
  styleUrls: ['./subway-table.component.css']
})
export class SubwayTableComponent implements OnInit {
  
  subways: Subway[];

  constructor(private subwayService: SubwayService) { }

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
        }else{
          alert("Something went wrong!");
        }
      });
  }

}
