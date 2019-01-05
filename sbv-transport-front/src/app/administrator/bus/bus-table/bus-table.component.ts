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
    
    this.busService.getBuses()
      .subscribe( data => {
        this.buses = data;
      });
      
  }

}
