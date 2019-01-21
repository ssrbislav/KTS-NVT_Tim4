import { Component, OnInit, ViewChild } from '@angular/core';
import { Trolley } from 'src/app/models/trolley.model';
import { TrolleyService } from 'src/app/services/trolley.service';
import { TrolleyTableComponent } from './trolley-table/trolley-table.component';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { TrolleyAddComponent } from './trolley-add/trolley-add.component';
import { TrolleySearchFilterComponent } from './trolley-search-filter/trolley-search-filter.component';
declare var ol: any;

@Component({
  selector: 'app-trolley',
  templateUrl: './trolley.component.html',
  styleUrls: ['./trolley.component.css']
})
export class TrolleyComponent implements OnInit {

  map: any;
  trolleys: Trolley[];
  @ViewChild("trolleyTable") table: TrolleyTableComponent;
  @ViewChild("trolleySearch") search: TrolleySearchFilterComponent;

  constructor(private trolleyService: TrolleyService,public dialog: MatDialog) { }

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

  loadBecauseFilter(filterBuses:Trolley[]){

    this.trolleys = filterBuses;
    this.loadMap(true);

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

  addTrolley(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana",
      added: false
      };

    const dialogRef = this.dialog.open(TrolleyAddComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.table.loadAllTrolleys();
    this.loadAllTrolleys(true);
    this.search.resetSearch();
  
  });
  }


}
