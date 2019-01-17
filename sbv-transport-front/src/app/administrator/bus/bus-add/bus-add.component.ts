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
  lines: Line[] = [];
  allLines: Line[];
  map: any;
  lon: any;
  lat:any;
  address:any;
  newBus: Bus;
  newLocation: MyLocation;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private busService: BusService, private locationService: LocationService) { }

  ngOnInit() {
    this.loadAlllines();
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadMap();
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
      if(this.allLines[i].line_type == "bus"){
        this.lines.push(this.allLines[i]);
      }
    }

  }

  nextClick(){

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

  loadMap(){
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/pin.png'
      }))
      });

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

    if(document.getElementById('lan').innerHTML != ""){
      this.mylocation.latitude = parseFloat(document.getElementById('lan').innerHTML);
      this.mylocation.longitude = parseFloat(document.getElementById('lon').innerHTML);
      this.mylocation.address = document.getElementById('address').innerHTML;
      this.mylocation.type = 'transport';
      this.mylocation.location_name = "Bus location";
      this.locationService.addLocation(this.mylocation)
      .subscribe( data => {
        if(data!= null){
          this.newLocation = data;
          this.addLocationToBus();
        }else{
          alert("Something went wrong!");
        }    
      });

    }else{
      alert("Successfully bus added!");
      this.dialogRef.close();
    }

  }

  addLocationToBus(){

    var addLocationTransport = new AddLocationToTransportDTO(this.newBus.id, this.newLocation.id);
    this.busService.addLocation(addLocationTransport)
    .subscribe( data => {
      if(data!= null){
        alert("Successfully bus added!");
        this.dialogRef.close();
      }else{
        alert("Something went wrong!");
      }
     
    });

  }

}
