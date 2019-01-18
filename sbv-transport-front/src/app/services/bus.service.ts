import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Bus } from '../models/bus.model';
import { TransportDTO } from '../models.dto/transport.dto';
import { AddLocationToTransportDTO } from '../models.dto/addLocationToTransportDTO.dto';
import { ChangeTransportDTO } from '../models.dto/changeTransport.dto';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=UTF-8'})
  };

@Injectable()
export class BusService{

  constructor(private http:HttpClient) {}

  private busUrl = 'http://localhost:8080/api/bus/';

  public getBuses() {
      return this.http.get<Bus[]>(this.busUrl);
    }
      
  public deleteBus(id: BigInteger){
    const url = `${this.busUrl + 'deleteBus'}/${id}`;
    return this.http.get<boolean>(url);

  }

  public addBus(bus: TransportDTO){
    return this.http.post<Bus>(this.busUrl + 'addBus',bus);                 
                
  }

  public addLocation(lo: AddLocationToTransportDTO){
    return this.http.post<Bus>(this.busUrl + 'addLocation',lo);                 
                
  }

  public updateBus(bus: ChangeTransportDTO){
    return this.http.post<Bus>(this.busUrl + 'updateBus',bus,httpOptions);                 
                
  }


  }