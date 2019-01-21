import { Component, OnInit ,ViewChild,Output, EventEmitter} from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { StationAddComponent } from './station-add/station-add.component';
import { StationTableComponent } from './station-table/station-table.component';
import { Station } from 'src/app/models/station.model';
import { StationService } from 'src/app/services/station.service';

declare var ol: any;

@Component({
  selector: 'app-station',
  templateUrl: './station.component.html',
  styleUrls: ['./station.component.css']
})
export class StationComponent implements OnInit {

  @ViewChild("appTable") table: StationTableComponent;
  map: any;
  stations: Station[];

  constructor(public dialog: MatDialog,private stationService: StationService) { }

  ngOnInit() {
    this.loadAllStations(false);
  }

  loadAllStations(i: boolean){
    this.stationService.getStations()
      .subscribe( data => {
        this.stations = data;
        this.loadMap(i);
      });
  }
  
  loadBecauseFilter(filterBuses:Station[]){

    this.stations = filterBuses;
    this.loadMap(true);

  }

  addStation(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana",
      added: false
      };

    const dialogRef = this.dialog.open(StationAddComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.table.loadAllStations();
    this.loadAllStations(true);
  

    });

  }

  loadMap(i:boolean){
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/station-map.png'
      }))
      });

    if(!i){
      this.map = new ol.Map({
        target: 'map2',
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
        var iconFeature = new ol.Feature({
          geometry: new ol.geom.Point(ol.proj.transform([value.location.longitude, value.location.latitude], 'EPSG:4326',
                  'EPSG:3857'))
    
          });
        markerSource.addFeature(iconFeature);
    
      })
    }else{

      const l= this.map.getLayers().getArray()[1];

      const markerSource2 = new ol.source.Vector();
    
      this.stations.forEach(function (value) {
        var iconFeature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform([value.location.longitude, value.location.latitude], 'EPSG:4326',
                'EPSG:3857'))
  
        });
        markerSource2.addFeature(iconFeature);
   
  
    })
      l.setSource(markerSource2);
    }
    
    
    
    
  }

  
  


}
