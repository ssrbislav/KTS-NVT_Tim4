import { Component, OnInit, Inject } from '@angular/core';
import { TransportDTO } from 'src/app/models.dto/transport.dto';
import { LocationDTO } from 'src/app/models.dto/location.dto';
import { Line } from 'src/app/models/line.model';
import { Trolley } from 'src/app/models/trolley.model';
import { MyLocation } from 'src/app/models/location.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { LineService } from 'src/app/services/line.service';
import { TrolleyService } from 'src/app/services/trolley.service';
import { LocationService } from 'src/app/services/location.service';
import { AddLocationToTransportDTO } from 'src/app/models.dto/addLocationToTransportDTO.dto';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { AltTimetableDTO } from 'src/app/models.dto/timetable.dto';
import { TimetableService } from 'src/app/services/timetable.service';
declare var ol: any; 

@Component({
  selector: 'app-trolley-add',
  templateUrl: './trolley-add.component.html',
  styleUrls: ['./trolley-add.component.css']
})
export class TrolleyAddComponent implements OnInit {

  show: string = 'trolley';
  showMap: boolean = true;
  trolley: TransportDTO = new TransportDTO();
  mylocation : LocationDTO = new LocationDTO();
  lineSelected: Line = new Line();
  selectedLocation: MyLocation = new MyLocation();
  lines: Line[] = [];
  allLines: Line[];
  map: any;
  newTrolley: Trolley= new Trolley();
  productForm: FormGroup;
  timetable : AltTimetableDTO = new AltTimetableDTO();

  

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private trolleyService: TrolleyService,
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
        this.loadTrolleyLines();
      });

  }

  loadTrolleyLines(){

    for (var i = 0; i < this.allLines.length; i++){
      if(this.allLines[i].line_type.toString() == "trolley"){
        this.lines.push(this.allLines[i]);
      }
    }

  }

  nextClick(){
    this.trolley.id_line = this.lineSelected.id;
    if(this.trolley.name == null){
      alert("Please write trolley name!");
    }else if(this.trolley.time_arrive == null){
      alert("Please write time arrive between stations!");
    }else if(this.trolley.id_line == null){
      alert("Please select line!");
    }else if(this.trolley.time_arrive <5){
      alert("Time arrive need to be => 5!")
    }else{
      this.show = 'location';
      this.showMap = false;
      
    } 
  }

  backClick(){
    this.show = 'trolley';
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
        src: 'assets/pictures/trolley-map.png'
      }))
      });

    if(!i){
      this.map = new ol.Map({
        target: 'mapLocT',
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

  addTrolley(){
    this.trolleyService.addTrolley(this.trolley)
    .subscribe( data => {
      if(data!= null){
        this.newTrolley = data;
        this.addLocation();
      }else{
        alert("Something went wrong!");
      }
     
    });
  }

  addLocation(){

    if(this.selectedLocation.id != undefined){
       this.addLocationToTrolley();

    }else{
      this.addTimetable();
    }

  }

  addLocationToTrolley(){

    var addLocationTransport = new AddLocationToTransportDTO(this.newTrolley.id, this.selectedLocation.id);
    this.trolleyService.addLocation(addLocationTransport)
    .subscribe( data => {
      if(data!= null){
        this.addTimetable();
      }else{
        alert("Something went wrong!");
      }
     
    });

  }

  addTimetable(){
    
    if(this.productForm.value.time.length ==1 && this.productForm.value.time[0].point == 'function Date() { [native code] }'){
      alert("Successfully trolley created!");
      this.dialogRef.close();
    }else{

      this.timetable.id_transport = this.newTrolley.id;
      this.timetable.transportType = 'trolley';
      this.timetable.timetable = [];
      

      for (var i = 0; i < this.productForm.value.time.length; i++){
        this.timetable.timetable.push(this.productForm.value.time[i].point);
     
      }
      
      this.timetableService.addTimetable(this.timetable)
        .subscribe( data => {
          alert("Successfully trolley added!");
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
