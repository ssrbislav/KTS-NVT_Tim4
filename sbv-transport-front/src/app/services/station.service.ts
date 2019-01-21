import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Station } from '../models/station.model';
import { StationDTO } from '../models.dto/station.dto';
import { changeStationDTO } from '../models.dto/changeStation.dto';
import { FilterSearchStationDTO } from '../models.dto/filterSearchStation.dto';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable()
export class StationService{

    constructor(private http:HttpClient) {}

    private stationUrl = 'http://localhost:8080/api/station/';

    public getStations() {
         return this.http.get<Station[]>(this.stationUrl);
    }
      
    public deleteStation(id: BigInteger){
        const url = `${this.stationUrl + 'deleteStation'}/${id}`;
        return this.http.get<boolean>(url);

  }

    public getStation(id: BigInteger){
        const url = `${this.stationUrl + 'getStation'}/${id}`;
        return this.http.get<Station>(url);

}

    public addStation(station: StationDTO){
        return this.http.post<Station>(this.stationUrl + 'addStation',station);                 
                    
    }

    public updateStation(station: changeStationDTO){
        return this.http.post<Station>(this.stationUrl + 'updateStation',station,httpOptions);                 
                    
      }

    public filterSearch(values: FilterSearchStationDTO){
        return this.http.post<Station[]>(this.stationUrl + 'searchFilter',values);                 
                    
      }
    

}