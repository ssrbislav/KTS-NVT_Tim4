import {Injectable} from '@angular/core';
import { TimetableDTO } from '../models.dto/timetable.dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'dataType': 'json'})
  };


@Injectable()
export class TimetableService{

    constructor(private http:HttpClient) {}

    private timetableUrl = 'http://localhost:8080/api/timetable/';

    public addTimetable(timetable: TimetableDTO){
        return this.http.post<String>(this.timetableUrl + 'addTimetable',timetable,httpOptions);                 
                    
      }
    

}