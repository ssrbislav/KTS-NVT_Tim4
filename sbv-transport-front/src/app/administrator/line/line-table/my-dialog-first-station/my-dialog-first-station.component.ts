import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Station } from 'src/app/models/station.model';
declare var ol: any; 

@Component({
  selector: 'app-my-dialog-first-station',
  templateUrl: './my-dialog-first-station.component.html',
  styleUrls: ['./my-dialog-first-station.component.css']
})
export class MyDialogFirstStationComponent implements OnInit {

  station: Station;
  show :string = 'exist';
  map: any;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>) { 
    
  }

  ngOnInit() {
    this.dialogRef.updateSize('80%', '80%'); 
    this.station = this.data.station;
    this.loadPage();
    
  }

  loadPage(){
    if(this.station == null){
      this.show = 'not';
    }else{
      this.loadMap();
    }
  }

  loadMap(){
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/bus-stop.png'
      }))
      });

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
        center: ol.proj.fromLonLat([this.station.location.longitude,this.station.location.latitude]),
        zoom: 15
      })
      
    });

    var iconFeature = new ol.Feature({
      geometry: new ol.geom.Point(ol.proj.transform([this.station.location.longitude, this.station.location.latitude], 'EPSG:4326',
              'EPSG:3857'))

    });
    markerSource.clear();
    markerSource.addFeature(iconFeature);

  }

}
