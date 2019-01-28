import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Administrator } from '../models/administrator.model';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  @Injectable()
  export class AdministratorService{

    constructor(private http:HttpClient) {}

    administratorUrl = 'http://localhost:8080/api/administrator/';

    public updateAdministrator(administrator: Administrator){
        return this.http.post<Administrator>(this.administratorUrl + 'updateAdministrator',administrator);
    }

    public findAdmin(id){
        const url = `${this.administratorUrl + 'findAdministrator'}/${id}`;
        return this.http.get<Administrator>(url);
  
      }
  }