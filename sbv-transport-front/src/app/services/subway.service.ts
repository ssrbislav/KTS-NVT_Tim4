import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subway } from '../models/subway.model';
import { TransportDTO } from '../models.dto/transport.dto';
import { AddLocationToTransportDTO } from '../models.dto/addLocationToTransportDTO.dto';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  @Injectable()
  export class SubwayService{

    constructor(private http:HttpClient) {}

    private subwayUrl = 'http://localhost:8080/api/subway/';

    public getSubways() {
        return this.http.get<Subway[]>(this.subwayUrl);
      }
    
    public deleteSubway(id: BigInteger){
      const url = `${this.subwayUrl + 'deleteSubway'}/${id}`;
      return this.http.get<boolean>(url);

    }

    public addSubway(subway: TransportDTO){
      return this.http.post<Subway>(this.subwayUrl + 'addSubway',subway);                 
                  
    }
  
    public addLocation(location: AddLocationToTransportDTO){
      return this.http.post<Subway>(this.subwayUrl + 'addLocation',location);                 
                  
    }

  }