import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Line } from '../models/line.model';

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
    const url = `${this.lineUrl + 'deleteSubway'}/${id}`;
    return this.http.get<boolean>(url);

    }

  }