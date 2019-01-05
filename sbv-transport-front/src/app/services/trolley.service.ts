import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Trolley } from '../models/trolley.model';


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

  }