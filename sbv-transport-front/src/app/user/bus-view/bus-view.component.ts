import { Component, OnInit,Inject, Input,Output ,EventEmitter} from '@angular/core';
import { LineService } from 'src/app/services/line.service';
import { Line } from 'src/app/models/line.model';
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/models/bus.model';
import { TimetableService } from 'src/app/services/timetable.service';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { AltTimetableDTO } from 'src/app/models.dto/timetable.dto';
import { FilterSearchLineDTO } from 'src/app/models.dto/filterSearchLine.dto';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { Timetable } from 'src/app/models/timetable.model';
import { Schedule } from 'src/app/models/schedule.model';
import { BuyTicketDTO } from 'src/app/models.dto/buyTicket.dto';
declare var ol: any;

@Component({
  selector: 'app-bus-view',
  templateUrl: './bus-view.component.html',
  styleUrls: ['./bus-view.component.css']
})
export class BusViewComponent implements OnInit {

  show: string = 'bus';
  showMap: boolean = true;
  lineSelected: Line = new Line();
  lineGot: Line;
  lines: Line[] = [];
  allLines: Line[] = [];
  timetables: Timetable[];
  result: Timetable[] = [];
  map: any;
  schedule: Set<Schedule>;
  times: Set<Date>;
  @Input() timeTable: any;
  @Output() filter = new EventEmitter<Timetable[]>();
  @Output() buyTicketDTO = new BuyTicketDTO();
  productForm: FormGroup;
  filterSearch: Line;
  id: BigInteger;
  buses: Bus[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private busService: BusService,
  private timetableService: TimetableService, private fb: FormBuilder) { }

  ngOnInit() {
    this.loadLines();
    this.loadMap(false);
    // this.dialogRef.updateSize('80%', '80%');
  }

  loadFilterSearch(){
    if(this.filterSearch!= null){
      console.log(this.filterSearch);
    this.lineService.getLine(this.filterSearch.id).subscribe(data => this.lineGot = data);
    this.loadTimetables(this.filterSearch);
    this.findBuses();
    // console.log(this.lineGot)
    // this.loadTimetables(this.filterSearch);
    }else{
      this.buses = [];
      this.timetables = [];
      this.loadMap(true);
    }
    
  }

  resetSearch(){
    this.filterSearch = null;
  }

  loadLines(){
    this.lineService.getLines()
      .subscribe( data => {
        for (var i = 0; i < data.length; i++) {
          if (data[i].line_type.toString() == "bus") {
            this.lines.push(data[i]);
            console.log(data[i]);
          }
        }
        // this.lines = data;
        // this.loadBusLines();
      });
  }

  loadAlllines(){
    this.lineService.getLines()
      .subscribe( data => {
        this.allLines = data;
        this.loadBusLines();
      });

  }

  loadBusLines(){

    for (var i = 0; i < this.allLines.length; i++){
      if(this.allLines[i].line_type.toString() == "bus"){
        this.lines.push(this.allLines[i]);
      }
    }

  }

  loadTimetables(line: Line) {
    console.log(line);
    console.log(line.name);
    console.log(line.timetable);
    this.timetables = line.timetable;
    for (var j = 0; j < line.timetable.length; j ++) {
      this.schedule = line.timetable[j].schedule;
      for (var i = 0; i < line.timetable[j].schedule.size; i++) {
        this.times.add(line.timetable[j].schedule[i].times);
      }
    }
  }

  buyTicket() {
    
  }

  findBuses(){
    this.buses = [];
    this.busService.getBuses()
    .subscribe(data =>{
      let allBuses: Bus[] = data;
      allBuses.forEach(value =>{
        if(value.line.id == this.filterSearch.id){
          this.buses.push(value);
        }
      });
      this.loadMap(true); 
    })

  }

  loadMap(i:boolean){
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/bus-map.png'
      }))
      });

    if(!i){
      this.map = new ol.Map({
        target: 'map',
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM()
          }),
          new ol.layer.Vector({
            source: markerSource,
            style: iconStyle,
            
          }),
        ],
        view: new ol.View({
          center: ol.proj.fromLonLat([19.833549,45.267136]),
          zoom: 13
        })
        
      });

  
    }else{

      const l= this.map.getLayers().getArray()[1];

      const markerSource2 = new ol.source.Vector();
    
      this.buses.forEach(function (value) {
        if(value.location!= null){
          var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([value.location.longitude, value.location.latitude], 'EPSG:4326',
                    'EPSG:3857'))
      
            });
          markerSource2.addFeature(iconFeature);
       
        }
      })
      l.setSource(markerSource2);
    } 
    
  }

}
