import { Component, OnInit } from '@angular/core';
import { Subway } from 'src/app/models/subway.model';
import { SubwayService } from 'src/app/services/subway.service';
declare var ol: any;

@Component({
  selector: 'app-subway',
  templateUrl: './subway.component.html',
  styleUrls: ['./subway.component.css']
})
export class SubwayComponent implements OnInit {

  map: any;
  subways: Subway[];

  constructor(private subwayService: SubwayService) { }

  ngOnInit() {
    this.loadAllSubways(false);
  }

  loadAllSubways(i: boolean){
    this.subwayService.getSubways()
      .subscribe( data => {
        this.subways = data;
        this.loadMap(i);
      });
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
        target: 'mapSubway',
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

      this.subways.forEach(function (value) {
        if(value.location!=null){
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
    
      this.subways.forEach(function (value) {
        if(value.location!=null){
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
