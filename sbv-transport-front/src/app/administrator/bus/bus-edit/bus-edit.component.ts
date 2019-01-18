import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Bus } from 'src/app/models/bus.model';
import { LocationDTO } from 'src/app/models.dto/location.dto';
import { MyLocation } from 'src/app/models/location.model';
import { LineService } from 'src/app/services/line.service';
import { BusService } from 'src/app/services/bus.service';
import { LocationService } from 'src/app/services/location.service';
import { ChangeTransportDTO } from 'src/app/models.dto/changeTransport.dto';
declare var ol: any; 

@Component({
  selector: 'app-bus-edit',
  templateUrl: './bus-edit.component.html',
  styleUrls: ['./bus-edit.component.css']
})
export class BusEditComponent implements OnInit {

  bus: Bus;
  show: string = 'bus';
  showMap: boolean = true;
  mylocation : LocationDTO = new LocationDTO();
  map: any;
  newBus: Bus;
  newLocation: MyLocation;
  changeBus: ChangeTransportDTO = new ChangeTransportDTO();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private busService: BusService, private locationService: LocationService) { }

  ngOnInit() {
    this.bus = this.data.bus;
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadMap(false);
  }


  nextClick(){

    if(this.bus.name == null){
      alert("Please write bus name!");
    }else if(this.bus.time_arrive == null){
      alert("Please write time arrive between stations!");
    }else if(this.bus.line == null){
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
          geometry: new ol.geom.Point(ol.proj.transform([this.bus.location.longitude, this.bus.location.latitude], 'EPSG:4326',
                'EPSG:3857'))
      
        });
          markerSource2.addFeature(iconFeature);  
      l.setSource(markerSource2);
    } 
    
  }

  editBus(){
    this.changeBus.id_transport = this.bus.id;
    this.changeBus.current_location = this.bus.location;
    this.changeBus.name = this.bus.name;
    this.changeBus.time_arrive = this.bus.time_arrive;
    this.changeBus.timetable = null;
    this.busService.updateBus(this.changeBus)
    .subscribe( data => {
      if(data!= null){
        alert("Successfully bus updated!");
        this.dialogRef.close();
        
      }else{
        alert("Something went wrong!");
      }
     
    });
  }

  

}
