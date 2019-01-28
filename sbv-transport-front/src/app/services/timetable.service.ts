import {Injectable} from '@angular/core';
import { AltTimetableDTO } from '../models.dto/timetable.dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Timetable } from '../models/timetable.model';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'dataType': 'json'})
  };


@Injectable()
export class TimetableService{

    constructor(private http:HttpClient) {}

    timetableUrl = 'http://localhost:8080/api/timetable/';

    public addTimetable(timetable:AltTimetableDTO){
        return this.http.post<Timetable>(this.timetableUrl + 'addAltTimetable',timetable,httpOptions);                 
                    
      }
    

}