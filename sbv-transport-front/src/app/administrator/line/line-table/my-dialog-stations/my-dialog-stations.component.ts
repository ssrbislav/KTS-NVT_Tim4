import { Component, OnInit ,Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Station } from 'src/app/models/station.model';
declare var ol: any; 

@Component({
  selector: 'app-my-dialog-stations',
  templateUrl: './my-dialog-stations.component.html',
  styleUrls: ['./my-dialog-stations.component.css']
})
export class MyDialogStationsComponent implements OnInit {

  stations: Station[];
  map: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>) { }

  ngOnInit() {
    this.dialogRef.updateSize('80%', '80%'); 
    this.stations = this.data.station_list;
    this.loadMap();
  }

  loadMap(){
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/station-map.png'
      }))
      });


    this.map = new ol.Map({
      target: 'map3',
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

    this.stations.forEach(function (value) {
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
