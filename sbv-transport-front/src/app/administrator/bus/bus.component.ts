import { Component, OnInit ,ViewChild} from '@angular/core';
import { Bus } from 'src/app/models/bus.model';
import { BusService } from 'src/app/services/bus.service';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { BusAddComponent } from './bus-add/bus-add.component';
import { BusTableComponent } from './bus-table/bus-table.component';
declare var ol: any;

@Component({
  selector: 'app-bus',
  templateUrl: './bus.component.html',
  styleUrls: ['./bus.component.css']
})
export class BusComponent implements OnInit {

  map: any;
  buses: Bus[];
  @ViewChild("busTable") table: BusTableComponent;

  constructor(private busService: BusService,public dialog: MatDialog) { }

  ngOnInit() {
    this.loadAllBuses(false);
  }

  loadAllBuses(i: boolean){
    this.busService.getBuses()
      .subscribe( data => {
        this.buses = data;
        this.loadMap(i);
      });
  }

  loadBecauseFilter(filterBuses:Bus[]){

    this.buses = filterBuses;
    this.loadMap(true);

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
        target: 'mapBus',
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

      this.buses.forEach(function (value) {
        if(value.location!= null){
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
    
      this.buses.forEach(function (value) {
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

  addBus(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana",
      added: false
      };

    const dialogRef = this.dialog.open(BusAddComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.table.loadAllBuses();
    this.loadAllBuses(true);
  
  });
  }

  

}
