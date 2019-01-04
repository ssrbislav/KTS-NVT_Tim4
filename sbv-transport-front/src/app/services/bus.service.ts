import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Bus } from '../models/bus.model';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  @Injectable()
  export class BusService{

    constructor(private http:HttpClient) {}

    private busUrl = 'http://localhost:8080/api/bus/';

    public getBuses() {
        return this.http.get<Bus[]>(this.busUrl);
      }

  }