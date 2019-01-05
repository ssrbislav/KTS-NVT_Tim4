import { Component, OnInit } from '@angular/core';
import { Trolley } from 'src/app/models/trolley.model';
import { TrolleyService } from 'src/app/services/trolley.service';

@Component({
  selector: 'app-trolley-table',
  templateUrl: './trolley-table.component.html',
  styleUrls: ['./trolley-table.component.css']
})
export class TrolleyTableComponent implements OnInit {

  trolleys: Trolley[];

  constructor(private trolleyService: TrolleyService) { }

  ngOnInit() {
    this.loadAllTrolleys();
  }

  loadAllTrolleys(){
    this.trolleyService.getTrolley()
    .subscribe( data => {
      this.trolleys = data;
    });
  }

  deleteTrolley(id: BigInteger){
    console.log(id);
    this.trolleyService.deleteTrolley(id)
      .subscribe( data => {
        if(data == true){
          alert("Trolley is deleted!");
          this.loadAllTrolleys();
        }else{
          alert("Something went wrong!");
        }
      });
  }

}
