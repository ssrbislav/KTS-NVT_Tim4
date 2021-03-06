import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { TransportDTO } from 'src/app/models.dto/transport.dto';
import { Line } from 'src/app/models/line.model';
import { LineService } from 'src/app/services/line.service';
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/models/bus.model';
import { LocationService } from 'src/app/services/location.service';
import { LocationDTO } from 'src/app/models.dto/location.dto';
import { MyLocation } from 'src/app/models/location.model';
import { AddLocationToTransportDTO } from 'src/app/models.dto/addLocationToTransportDTO.dto';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { AltTimetableDTO } from 'src/app/models.dto/timetable.dto';
import { TimetableService } from 'src/app/services/timetable.service';
declare var ol: any; 

@Component({
  selector: 'app-bus-add',
  templateUrl: './bus-add.component.html',
  styleUrls: ['./bus-add.component.css']
})
export class BusAddComponent implements OnInit {

  show: string = 'bus';
  showMap: boolean = true;
  bus: TransportDTO = new TransportDTO();
  mylocation : LocationDTO = new LocationDTO();
  lineSelected: Line = new Line();
  selectedLocation: MyLocation = new MyLocation();
  lines: Line[] = [];
  allLines: Line[];
  map: any;
  newBus: Bus= new Bus();
  productForm: FormGroup;
  timetable : AltTimetableDTO = new AltTimetableDTO();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private busService: BusService,
  private timetableService: TimetableService, private fb: FormBuilder) { }

  ngOnInit() {
    this.loadAlllines();
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadMap(false);

    this.productForm = this.fb.group({
      time: this.fb.array([this.fb.group({point:Date})])
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

  nextClick(){
    this.bus.id_line = this.lineSelected.id;
    if(this.bus.name == null){
      alert("Please write bus name!");
    }else if(this.bus.time_arrive == null){
      alert("Please write time arrive between stations!");
    }else if(this.bus.id_line == null){
      alert("Please select line!");
    }else if(this.bus.time_arrive <5){
      alert("Time arrive need to be => 5!")
    }else{
      this.show = 'location';
      this.showMap = false;
      
    } 
  }

  backClick(){
    this.show = 'bus';
    this.showMap = true;
  }

  next2Click(){
    this.show = 'timetable';
    this.showMap = true;
  }

  back2Click(){
    this.show = 'location';
    this.showMap = false;
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
        target: 'mapLoc',
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
      var iconFeature = new ol.Feature({
          geometry: new ol.geom.Point(ol.proj.transform([this.selectedLocation.longitude, this.selectedLocation.latitude], 'EPSG:4326',
                'EPSG:3857'))
      
        });
          markerSource2.addFeature(iconFeature);  
      l.setSource(markerSource2);
    } 
    
  }

  addBus(){
    this.busService.addBus(this.bus)
    .subscribe( data => {
      if(data!= null){
        this.newBus = data;
        this.addLocation();
      }else{
        alert("Something went wrong!");
      }
     
    });
  }

  addLocation(){

    if(this.selectedLocation.id != undefined){
      this.addLocationToBus();
       
    }else{
      this.addTimetable();
      //this.dialogRef.close();
    }

  }

  addLocationToBus(){

    var addLocationTransport = new AddLocationToTransportDTO(this.newBus.id, this.selectedLocation.id);
    this.busService.addLocation(addLocationTransport)
    .subscribe( data => {
      if(data!= null){
        this.addTimetable();
        //this.dialogRef.close();
    
      }else{
        alert("Something went wrong!");
      }
     
    });

  }

  addTimetable(){
    
    if(this.productForm.value.time.length ==1 && this.productForm.value.time[0].point == 'function Date() { [native code] }'){
      alert("Successfully bus created!");
      this.dialogRef.close();
    }else{

      this.timetable.id_transport = this.newBus.id;
      this.timetable.transportType = 'bus';
      this.timetable.timetable = [];
      

      for (var i = 0; i < this.productForm.value.time.length; i++){
        this.timetable.timetable.push(this.productForm.value.time[i].point);
     
      }
      
      this.timetableService.addTimetable(this.timetable)
        .subscribe( data => {
          alert("Successfully bus added!");
          this.dialogRef.close();
          
        });


      }

  }

  get Times() {
    return this.productForm.get('time') as FormArray;
  }

  addSellingPoint() {
    this.Times.push(this.fb.group({point:Date}));
  }

}
