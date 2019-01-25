import { Component, OnInit } from '@angular/core';
import { BusService } from 'src/app/services/bus.service';
import { SubwayService } from 'src/app/services/subway.service';
import { TrolleyService } from 'src/app/services/trolley.service';
import { LineService } from 'src/app/services/line.service';
import { StationService } from 'src/app/services/station.service';
import { ControllerService } from 'src/app/services/controller.service';
import { ReportLineDTO } from 'src/app/models.dto/reportLine.dto';
import { Line } from 'src/app/models/line.model';
import { Bus } from 'src/app/models/bus.model';
import { Subway } from 'src/app/models/subway.model';
import { Trolley } from 'src/app/models/trolley.model';
import { ReportTicketDTO } from 'src/app/models.dto/reportTicket.dto';
import { ReportResultTicketDTO } from 'src/app/models.dto/reportResultTicket.dto';
import { PricelistService } from 'src/app/services/pricelist.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  size_bus: number;
  size_subway: number;
  size_trolley: number;
  size_line: number;
  size_station: number;
  size_controller: number;
  list_bus_table: ReportLineDTO[] = [];
  list_subway_table : ReportLineDTO [] = [];
  list_trolley_table : ReportLineDTO[]= [];
  list_lines: Line[] = [];
  list_buses: Bus[] = [];
  list_subways: Subway[] = [];
  list_trolleys: Trolley[] = [];
  reportTicket: ReportTicketDTO = new ReportTicketDTO(null,null);
  finalReportTicket: ReportResultTicketDTO[] = [];

  constructor(private busService: BusService, private subwayService:SubwayService, private trolleyService: TrolleyService,
    private lineService: LineService, private stationService: StationService, private controllerService: ControllerService,
    private pricelistService: PricelistService) { }

  ngOnInit() {
    this.loadAllBuses();
    this.loadAllStations();
    this.loadAllControllers();
    
  }

  loadAllBuses(){
    this.busService.getBuses()
    .subscribe( data => {
      this.size_bus = data.length;
      this.list_buses = data;
      this.loadAllSubways();    
    });

  }

  loadAllSubways(){
    this.subwayService.getSubways()
    .subscribe( data => {
      this.size_subway = data.length;
      this.list_subways = data;
      this.loadAllTrolleys();    
    });

  }

  loadAllTrolleys(){
    this.trolleyService.getTrolley()
    .subscribe( data => {
      this.size_trolley = data.length;
      this.list_trolleys = data;
      this.loadAllLines();    
    });
  }

  loadAllLines(){
    this.lineService.getLines()
    .subscribe( data => {
      this.size_line = data.length;
      this.list_lines = data;
      this.loadTables();   
    });
  }

  loadAllStations(){
    this.stationService.getStations()
    .subscribe( data => {
      this.size_station = data.length;    
    });
  }

  loadAllControllers(){
    this.controllerService.getControllers()
    .subscribe( data => {
      this.size_controller = data.length;    
    });
  }

  loadTables(){
    this.list_lines.forEach(value =>{
      if(value.line_type.valueOf().toString()== "bus"){
        this.addBusList(value);
      }else if(value.line_type.valueOf().toString()== "subway"){
        this.addSubwayList(value);
      }else if(value.line_type.valueOf().toString()== "trolley"){
        this.addTrolleyList(value);
      }
    })

  }

  addBusList(line: Line){
    var lineReport = new ReportLineDTO();
    lineReport.line = line;
    lineReport.numb_late = 0;
    lineReport.numb_on_time = 0;
    lineReport.numb_vehicles = 0;
    this.list_buses.forEach(value =>{
      if(value.line.id == line.id){
        lineReport.numb_vehicles = lineReport.numb_vehicles + 1;
        if(value.late){
          lineReport.numb_late = lineReport.numb_late + 1;
        }else{
          lineReport.numb_on_time = lineReport.numb_on_time + 1
        }
      }
    })

    this.list_bus_table.push(lineReport);

  }

  addSubwayList(line: Line){
    var lineReport = new ReportLineDTO();
    lineReport.line = line;
    lineReport.numb_late = 0;
    lineReport.numb_on_time = 0;
    lineReport.numb_vehicles = 0;
    this.list_subways.forEach(value =>{
      if(value.line.id == line.id){
        lineReport.numb_vehicles = lineReport.numb_vehicles + 1;
        if(value.late){
          lineReport.numb_late = lineReport.numb_late + 1;
        }else{
          lineReport.numb_on_time = lineReport.numb_on_time + 1
        }
      }
    })

    this.list_subway_table.push(lineReport);

  }

  addTrolleyList(line: Line){
    var lineReport = new ReportLineDTO();
    lineReport.line = line;
    lineReport.numb_late = 0;
    lineReport.numb_on_time = 0;
    lineReport.numb_vehicles = 0;
    this.list_trolleys.forEach(value =>{
      if(value.line.id == line.id){
        lineReport.numb_vehicles = lineReport.numb_vehicles + 1;
        if(value.late){
          lineReport.numb_late = lineReport.numb_late + 1;
        }else{
          lineReport.numb_on_time = lineReport.numb_on_time + 1
        }
      }
    })

    this.list_trolley_table.push(lineReport);

  }

  searchTickets(){
    if(this.reportTicket.start_date == null){
      alert("Please pick start date!");
    }else if(this.reportTicket.finished_date == null){
      alert("Please pick finish date");
    }else{
      this.pricelistService.reportTicket(this.reportTicket)
    .subscribe( data => {
      this.finalReportTicket = data;    
    });

    }

  }

}
