import { Component, OnInit , Inject, ViewChild, NgZone} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { LocationService } from 'src/app/services/location.service';
import { LocationDTO } from 'src/app/models.dto/location.dto';
import { StationDTO } from 'src/app/models.dto/station.dto';
declare var ol: any;
declare var address :string;



@Component({
  selector: 'app-station-add',
  templateUrl: './station-add.component.html',
  styleUrls: ['./station-add.component.css']
})
export class StationAddComponent implements OnInit {

  mylocation : LocationDTO = new LocationDTO();
  station : StationDTO = new StationDTO();
  map: any;
  lon: any;
  lat:any;
  address:any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private locationService: LocationService, private ngZone: NgZone) { }

  ngOnInit() {
    this.dialogRef.updateSize('80%', '80%'); 
    
    const markerSource = new ol.source.Vector();
    
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        src: 'assets/pictures/pin.png'
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
        center: ol.proj.fromLonLat([19.833549,45.267136]),
        zoom: 15
      })
      
      });

      this.map.on('click', function (args) {
        console.log(args.coordinate);
        var lonlat = ol.proj.transform(args.coordinate, 'EPSG:3857', 'EPSG:4326');
        console.log(lonlat);
        this.lon = lonlat[0];
        this.lat = lonlat[1];
        alert(`lat: ${this.lat} long: ${this.lon}`);
        var iconFeatures = [];
  
        var iconFeature = new ol.Feature({
          geometry: new ol.geom.Point(ol.proj.transform([this.lon, this.lat], 'EPSG:4326',
                'EPSG:3857'))

        });

        markerSource.clear();
        markerSource.addFeature(iconFeature);
        reverseGeocode(this.lon,this.lat); 
        

      });

      function reverseGeocode(lon, lat) {
        fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + lon + '&lat=' + lat)
          .then(function(response) {
                 return response.json();
             })
             .then(function(json) {
                console.log(json.display_name);
                this.ngZone.run(() => {
                  address = json.display_name;
                })
                // this.address = json.display_name;
                alert(json.display_name)
             });
     }
    
  }


  addStation(){
    this.mylocation.latitude = this.lat;
    this.mylocation.longitude = this.lon;
    this.mylocation.address = this.address;
    this.locationService.addLocation(this.mylocation)
    .subscribe( data => {
       if(data == "Location has been successfully created!"){
         alert("Kreirana lokacije");
       }
        // this.dialogRef.close();  

    });

  }

}
