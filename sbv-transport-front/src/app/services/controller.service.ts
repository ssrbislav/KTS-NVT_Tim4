import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Controller } from '../models/controller.model';
import { throwError } from 'rxjs';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  @Injectable()
  export class ControllerService{

    constructor(private http:HttpClient) {}

    private controllerUrl = 'http://localhost:8080/api/controller/';

    public getControllers() {
        return this.http.get<Controller[]>(this.controllerUrl);
      }
      
    public deleteController(id: BigInteger){
      const url = `${this.controllerUrl + 'deleteController'}/${id}`;
      return this.http.get<boolean>(url);

    }

    public addController(controller: Controller){
      return this.http.post(this.controllerUrl + 'addController',controller);
                      
                      
    }

  }