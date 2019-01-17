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
  lines: Line[] = [];
  allLines: Line[];
  map: any;
  lon: any;
  lat:any;
  address:any;
  newTrolley: Trolley;
  newLocation: MyLocation;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private trolleyService: TrolleyService, private locationService: LocationService) { }

  ngOnInit() {
    this.loadAlllines();
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadMap();
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

  loadMap(){
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/pin.png'
      }))
      });

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
        zoom: 15
      })
      
      });

      this.map.on('click', function (args) {
        console.log(args.coordinate);
        var lonlat = ol.proj.transform(args.coordinate, 'EPSG:3857', 'EPSG:4326');
        console.log(lonlat);
        this.lon = lonlat[0];
        this.lat = lonlat[1];
        //alert(`lat: ${this.lat} long: ${this.lon}`);
        var iconFeatures = [];
  
        var iconFeature = new ol.Feature({
          geometry: new ol.geom.Point(ol.proj.transform([this.lon, this.lat], 'EPSG:4326',
                'EPSG:3857'))

        });
        document.getElementById('lon').innerHTML = lonlat[0];
        document.getElementById('lan').innerHTML = lonlat[1];

        markerSource.clear();
        markerSource.addFeature(iconFeature);
        reverseGeocode(this.lon,this.lat);
        
      });

      function reverseGeocode(lon, lat) {
        fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + lon + '&lat=' + lat)
          .then(function(response) {
                 return response.json();
             })
             .then(function(json) {
                console.log(json.display_name);
                document.getElementById('address').innerHTML  = json.display_name;
             });
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

    if(document.getElementById('lan').innerHTML != ""){
      this.mylocation.latitude = parseFloat(document.getElementById('lan').innerHTML);
      this.mylocation.longitude = parseFloat(document.getElementById('lon').innerHTML);
      this.mylocation.address = document.getElementById('address').innerHTML;
      this.mylocation.type = 'transport';
      this.mylocation.location_name = "Trolley location";
      this.locationService.addLocation(this.mylocation)
      .subscribe( data => {
        if(data!= null){
          this.newLocation = data;
          this.addLocationToTrolley();
        }else{
          alert("Something went wrong!");
        }    
      });

    }else{
      alert("Successfully trolley added!");
      this.dialogRef.close();
    }

  }

  addLocationToTrolley(){

    var addLocationTransport = new AddLocationToTransportDTO(this.newTrolley.id, this.newLocation.id);
    this.trolleyService.addLocation(addLocationTransport)
    .subscribe( data => {
      if(data!= null){
        alert("Successfully trolley added!");
        this.dialogRef.close();
      }else{
        alert("Something went wrong!");
      }
     
    });

  }
}
