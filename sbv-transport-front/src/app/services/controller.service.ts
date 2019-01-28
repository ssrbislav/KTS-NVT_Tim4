import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Controller } from '../models/controller.model';
import {catchError} from 'rxjs/operators';
import { throwError} from 'rxjs';
import { FilterSearchControllerDTO } from '../models.dto/filterSearchController.dto';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  @Injectable()
  export class ControllerService{

    constructor(private http:HttpClient) {}

    controllerUrl = 'http://localhost:8080/api/controller/';

    public getControllers() {
        return this.http.get<Controller[]>(this.controllerUrl);
      }

    public getController(username: string) {
      return this.http.get<Controller>(`${this.controllerUrl + 'getController'}/${username}`);
    }
      
    public deleteController(id: BigInteger){
      const url = `${this.controllerUrl + 'deleteController'}/${id}`;
      return this.http.get<boolean>(url);

    }

    public addController(controller: Controller){
      return this.http.post<Controller>(this.controllerUrl + 'addController',controller)
      .pipe(
        catchError(e => throwError(e))
      );                 
                      
    }

    public filterSearch(values: FilterSearchControllerDTO){
      return this.http.post<Controller[]>(this.controllerUrl + 'searchFilter',values);                 
                  
    }

    public checkTicket(id: BigInteger) {
      return this.http.get<boolean>(`${this.controllerUrl + 'checkTicket'}/${id}`);
    }

    public blockTicket(id: BigInteger) {
      return this.http.get<boolean>(`${this.controllerUrl + 'blockTicket'}/${id}`);
    }

    public unblockTicket(id: BigInteger) {
      return this.http.get<boolean>(`${this.controllerUrl + 'unblockTicket'}/${id}`);
    }

  }