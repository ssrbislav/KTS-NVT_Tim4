import { Component, OnInit } from '@angular/core';
import { Bus } from 'src/app/models/bus.model';
import { BusService } from 'src/app/services/bus.service';

@Component({
  selector: 'app-bus-table',
  templateUrl: './bus-table.component.html',
  styleUrls: ['./bus-table.component.css']
})
export class BusTableComponent implements OnInit {

  buses: Bus[];

  constructor(private busService:BusService) { }

  ngOnInit() { 
    this.loadAllBuses();     
  }
  
  loadAllBuses(){
    this.busService.getBuses()
      .subscribe( data => {
        this.buses = data;
      });
  }

  deleteBus(id: BigInteger){
    console.log(id);
    this.busService.deleteBus(id)
      .subscribe( data => {
        if(data == true){
          alert("Bus is deleted!");
          this.loadAllBuses();
        }else{
          alert("Something went wrong!");
        }
      });
  }

}
