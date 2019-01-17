import { Component, OnInit, Inject } from '@angular/core';
import { TransportDTO } from 'src/app/models.dto/transport.dto';
import { LocationDTO } from 'src/app/models.dto/location.dto';
import { Line } from 'src/app/models/line.model';
import { Subway } from 'src/app/models/subway.model';
import { MyLocation } from 'src/app/models/location.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { LineService } from 'src/app/services/line.service';
import { SubwayService } from 'src/app/services/subway.service';
import { LocationService } from 'src/app/services/location.service';
import { AddLocationToTransportDTO } from 'src/app/models.dto/addLocationToTransportDTO.dto';
declare var ol: any; 

@Component({
  selector: 'app-subway-add',
  templateUrl: './subway-add.component.html',
  styleUrls: ['./subway-add.component.css']
})
export class SubwayAddComponent implements OnInit {

  show: string = 'subway';
  showMap: boolean = true;
  subway: TransportDTO = new TransportDTO();
  mylocation : LocationDTO = new LocationDTO();
  lineSelected: Line = new Line();
  selectedLocation: MyLocation = new MyLocation();
  lines: Line[] = [];
  allLines: Line[];
  map: any;
  newSubway: Subway;
  newLocation: MyLocation;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private subwayService: SubwayService, private locationService: LocationService) { }

  ngOnInit() {
    this.loadAlllines();
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadMap(false);
  }

  loadAlllines(){
    this.lineService.getLines()
      .subscribe( data => {
        this.allLines = data;
        this.loadSubwayLines();
      });

  }

  loadSubwayLines(){

    for (var i = 0; i < this.allLines.length; i++){
      if(this.allLines[i].line_type.toString() == "subway"){
        this.lines.push(this.allLines[i]);
      }
    }

  }

  nextClick(){
    this.subway.id_line = this.lineSelected.id;
    if(this.subway.name == null){
      alert("Please write subway name!");
    }else if(this.subway.time_arrive == null){
      alert("Please write time arrive between stations!");
    }else if(this.subway.id_line == null){
      alert("Please select line!");
    }else if(this.subway.time_arrive <5){
      alert("Time arrive need to be => 5!")
    }else{
      this.show = 'location';
      this.showMap = false;
      
    } 
  }

  backClick(){
    this.show = 'subway';
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
        src: 'assets/pictures/subway-map.png'
      }))
      });

    if(!i){
      this.map = new ol.Map({
        target: 'mapLocS',
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


  addSubway(){
    this.subwayService.addSubway(this.subway)
    .subscribe( data => {
      if(data!= null){
        this.newSubway = data;
        this.addLocation();
      }else{
        alert("Something went wrong!");
      }
     
    });
  }

  addLocation(){

    if(this.selectedLocation.id != undefined){
      this.mylocation.latitude = this.selectedLocation.latitude;
      this.mylocation.longitude = this.selectedLocation.longitude
      this.mylocation.address =this.selectedLocation.address;
      this.mylocation.type = 'transport';
      this.mylocation.location_name = "Subway location";
      this.locationService.addLocation(this.mylocation)
      .subscribe( data => {
        if(data!= null){
          this.newLocation = data;
          this.addLocationToSubway();
        }else{
          alert("Something went wrong!");
        }    
      });

    }else{
      alert("Successfully subway added!");
      this.dialogRef.close();
    }

  }

  addLocationToSubway(){

    var addLocationTransport = new AddLocationToTransportDTO(this.newSubway.id, this.newLocation.id);
    this.subwayService.addLocation(addLocationTransport)
    .subscribe( data => {
      if(data!= null){
        alert("Successfully subway added!");
        this.dialogRef.close();
      }else{
        alert("Something went wrong!");
      }
     
    });

  }

}
