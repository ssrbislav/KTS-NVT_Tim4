import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Line } from '../models/line.model';
import { LineDTO } from '../models.dto/line.dto';
import { AddStationToList } from '../models.dto/addStationToList.dto';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


@Injectable()
export class LineService{

  constructor(private http:HttpClient) {}

  private lineUrl = 'http://localhost:8080/api/line/';

  public getLines() {
      return this.http.get<Line[]>(this.lineUrl);
  }
      
  public deleteLine(id: BigInteger){
      const url = `${this.lineUrl + 'deleteLine'}/${id}`;
      return this.http.get<boolean>(url);

  }
  public addLine(line: LineDTO){
    return this.http.post<Line>(this.lineUrl + 'addLine',line);                 
                
  }

  public addStation(stations: AddStationToList[]){
    return this.http.post<Line>(this.lineUrl + 'addListStation',stations);                 

  }

  public updateLine(line: AddStationToList[]){
    return this.http.post<Line>(this.lineUrl + 'updateLine',line,httpOptions);                 
                
  }

  }