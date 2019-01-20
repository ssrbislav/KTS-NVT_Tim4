import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Trolley } from '../models/trolley.model';
import { TransportDTO } from '../models.dto/transport.dto';
import { AddLocationToTransportDTO } from '../models.dto/addLocationToTransportDTO.dto';
import { ChangeTransportDTO } from '../models.dto/changeTransport.dto';
import { FilterSearchDTO } from '../models.dto/filterSearch.dto';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  @Injectable()
  export class TrolleyService{

    constructor(private http:HttpClient) {}

    private trolleyUrl = 'http://localhost:8080/api/trolley/';

    public getTrolley() {
        return this.http.get<Trolley[]>(this.trolleyUrl);
      }

    public deleteTrolley(id: BigInteger){
      const url = `${this.trolleyUrl + 'deleteTrolley'}/${id}`;
      return this.http.get<boolean>(url);

    }

    public addTrolley(trolley: TransportDTO){
      return this.http.post<Trolley>(this.trolleyUrl + 'addTrolley',trolley);                 
                  
    }
  
    public addLocation(lo: AddLocationToTransportDTO){
      return this.http.post<Trolley>(this.trolleyUrl + 'addLocation',lo);                 
                  
    }

    public updateTrolley(trolley: ChangeTransportDTO){
      return this.http.post<Trolley>(this.trolleyUrl + 'updateTrolley',trolley,httpOptions);                 
                  
    }

    public filterSearch(values: FilterSearchDTO){
      return this.http.post<Trolley[]>(this.trolleyUrl + 'searchFilter',values);                 
                  
    }
  

  }