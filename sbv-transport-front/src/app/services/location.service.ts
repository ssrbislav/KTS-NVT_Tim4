import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Line } from '../models/line.model';
import { MyLocation } from '../models/location.model';
import { LocationDTO } from '../models.dto/location.dto';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


@Injectable()
export class LocationService{

constructor(private http:HttpClient) {}

private locationUrl = 'http://localhost:8080/api/location/';

public getLocations() {
    return this.http.get<MyLocation[]>(this.locationUrl);
 }
    
public deleteLocation(id: BigInteger){
    const url = `${this.locationUrl + 'deleteLocation'}/${id}`;
    return this.http.get<boolean>(url);

}

public addLocation(location: LocationDTO){
    return this.http.post(this.locationUrl + 'addLocation',location);                 
                    
}

}