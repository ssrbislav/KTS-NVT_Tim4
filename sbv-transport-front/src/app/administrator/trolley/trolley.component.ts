import { Component, OnInit } from '@angular/core';
import { Trolley } from 'src/app/models/trolley.model';
import { TrolleyService } from 'src/app/services/trolley.service';
declare var ol: any;

@Component({
  selector: 'app-trolley',
  templateUrl: './trolley.component.html',
  styleUrls: ['./trolley.component.css']
})
export class TrolleyComponent implements OnInit {

  map: any;
  trolleys: Trolley[];

  constructor(private trolleyService: TrolleyService) { }

  ngOnInit() {
    this.loadAllTrolleys(false);
  }

  loadAllTrolleys(i: boolean){
    this.trolleyService.getTrolley()
      .subscribe( data => {
        this.trolleys = data;
        this.loadMap(i);
      });
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
        target: 'mapTrolley',
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

      this.trolleys.forEach(function (value) {
        if(value.location != null){
          var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([value.location.longitude, value.location.latitude], 'EPSG:4326',
                    'EPSG:3857'))
      
            });
          markerSource.addFeature(iconFeature);
      
        }
        
      })
    }else{

      const l= this.map.getLayers().getArray()[1];

      const markerSource2 = new ol.source.Vector();
    
      this.trolleys.forEach(function (value) {
        if(value.location!= null){
          var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([value.location.longitude, value.location.latitude], 'EPSG:4326',
                    'EPSG:3857'))
      
            });
            markerSource2.addFeature(iconFeature);
       
        }
        
  
    })
      l.setSource(markerSource2);
    } 
    
  }


}
