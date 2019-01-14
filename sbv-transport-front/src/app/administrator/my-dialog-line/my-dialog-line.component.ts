import { Component, OnInit,Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Line } from 'src/app/models/line.model';
import { Station } from 'src/app/models/station.model';
import { StationService } from 'src/app/services/station.service';
declare var ol: any; 

@Component({
  selector: 'app-my-dialog',
  templateUrl: './my-dialog-line.component.html',
  styleUrls: ['./my-dialog-line.component.css']
})
export class MyDialogComponent implements OnInit {

  modalTitle: string;
  line: Line;
  fstation: string = "";
  s: Station;
  map: any;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private dialogRef: MatDialogRef<any>, private stationService: StationService) {
   this.modalTitle = data.title; 
   console.log(data)
   }
   
  ngOnInit() {
    this.line = this.data.line;
    this.dialogRef.updateSize('80%', '80%'); 
    this.loadFirstStation();
  }

  loadFirstStation(){
    if(this.line.first_station != null){
      this.stationService.getStation(this.line.first_station)
      .subscribe( data => {
        this.s =  data; 
        this.fstation = this.s.location.location_name;
        this.loadMap();
      });
    }
    
  }

  loadMap(){
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/station-map.png'
      }))
      });


    this.map = new ol.Map({
      target: 'map5',
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
        zoom: 12
      })
      
    });

    this.line.station_list.forEach(function (value) {
      if(value.deleted == false){
        var iconFeature = new ol.Feature({
          geometry: new ol.geom.Point(ol.proj.transform([value.location.longitude, value.location.latitude], 'EPSG:4326',
                  'EPSG:3857'))
    
          });
    
        markerSource.addFeature(iconFeature);
      }
      
  
    })
  }

}
