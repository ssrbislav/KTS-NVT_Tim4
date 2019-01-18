import { Component, OnInit, Inject } from '@angular/core';
import { Trolley } from 'src/app/models/trolley.model';
import { LocationDTO } from 'src/app/models.dto/location.dto';
import { MyLocation } from 'src/app/models/location.model';
import { ChangeTransportDTO } from 'src/app/models.dto/changeTransport.dto';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { LineService } from 'src/app/services/line.service';
import { TrolleyService } from 'src/app/services/trolley.service';
import { LocationService } from 'src/app/services/location.service';
declare var ol: any; 

@Component({
  selector: 'app-trolley-edit',
  templateUrl: './trolley-edit.component.html',
  styleUrls: ['./trolley-edit.component.css']
})
export class TrolleyEditComponent implements OnInit {

  trolley: Trolley;
  show: string = 'trolley';
  showMap: boolean = true;
  mylocation : LocationDTO = new LocationDTO();
  map: any;
  newTrolley: Trolley;
  newLocation: MyLocation;
  changeTrolley: ChangeTransportDTO = new ChangeTransportDTO();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private trolleyService: TrolleyService, private locationService: LocationService) { }

  ngOnInit() {
    this.trolley = this.data.trolley;
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadMap(false);
  }


  nextClick(){

    if(this.trolley.name == ""){
      alert("Please write trolley name!");
    }else if(this.trolley.time_arrive == null){
      alert("Please write time arrive between stations!");
    }else if(this.trolley.line == null){
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
          geometry: new ol.geom.Point(ol.proj.transform([this.trolley.location.longitude, this.trolley.location.latitude], 'EPSG:4326',
                'EPSG:3857'))
      
        });
          markerSource2.addFeature(iconFeature);  
      l.setSource(markerSource2);
    } 
    
  }

  editTrolley(){
    this.changeTrolley.id_transport = this.trolley.id;
    this.changeTrolley.current_location = this.trolley.location;
    this.changeTrolley.name = this.trolley.name;
    this.changeTrolley.time_arrive = this.trolley.time_arrive;
    this.changeTrolley.timetable = null;
    this.trolleyService.updateTrolley(this.changeTrolley)
    .subscribe( data => {
      if(data!= null){
        alert("Successfully trolley updated!");
        this.dialogRef.close();
        
      }else{
        alert("Something went wrong!");
      }
     
    });
  }


}
