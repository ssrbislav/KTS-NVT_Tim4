import { Component, OnInit , ViewChild} from '@angular/core';
import { Subway } from 'src/app/models/subway.model';
import { SubwayService } from 'src/app/services/subway.service';
import { SubwayTableComponent } from './subway-table/subway-table.component';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { SubwayAddComponent } from './subway-add/subway-add.component';
declare var ol: any;

@Component({
  selector: 'app-subway',
  templateUrl: './subway.component.html',
  styleUrls: ['./subway.component.css']
})
export class SubwayComponent implements OnInit {

  map: any;
  subways: Subway[];
  @ViewChild("subwayTable") table: SubwayTableComponent;

  constructor(private subwayService: SubwayService,public dialog: MatDialog) { }

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

  loadBecauseFilter(filterBuses:Subway[]){

    this.subways = filterBuses;
    this.loadMap(true);

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

  addSubway(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana",
      added: false
      };

    const dialogRef = this.dialog.open(SubwayAddComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.table.loadAllSubways();
    this.loadAllSubways(true);
  
  });
  }



}
