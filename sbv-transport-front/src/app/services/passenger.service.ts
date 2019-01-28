import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Passenger } from '../models/passenger.model';
import {catchError} from 'rxjs/operators';
import { throwError} from 'rxjs';
// import { FilterSearchPassengerDTO } from '../models.dto/filterSearchPassenger.dto';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  @Injectable()
  export class PassengerService{

    constructor(private http:HttpClient) {}

    passengerUrl = 'http://localhost:8080/api/passenger/';

    public getPassengers() {
        return this.http.get<Passenger[]>(this.passengerUrl);
    }

    public getPassengerById(id: BigInteger) {
      return this.http.get<Passenger>(`${this.passengerUrl + 'getPassengerByID'}/${id}`);
    }

    public getPassenger(username: string) {
      return this.http.get<Passenger>(`${this.passengerUrl + 'getPassenger'}/${username}`);
    }
      
    public deletePassenger(id: BigInteger){
      const url = `${this.passengerUrl + 'deletePassenger'}/${id}`;
      return this.http.get<boolean>(url);

    }

    public addPassenger(passenger: Passenger){
      return this.http.post<Passenger>(this.passengerUrl + 'addPassenger',passenger)
      .pipe(
        catchError(e => throwError(e))
      );                 
                      
    }

    public updatePassenger(passenger: Passenger, id: BigInteger){
      const url = `${this.passengerUrl + 'updatePassenger'}/${id}`;
      return this.http.post<Passenger>(url, passenger);
    }

    // public filterSearch(values: FilterSearchPassengerDTO){
    //   return this.http.post<Passenger[]>(this.passengerUrl + 'searchFilter',values);                 
                  
    // }

  }