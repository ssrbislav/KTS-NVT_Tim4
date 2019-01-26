import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MyDocument } from '../models/document.model';

@Injectable()
export class DocumentService{

    constructor(private http:HttpClient) {}

    private documentUrl = 'http://localhost:8080/api/document/';

    public getDocuments() {
      return this.http.get<MyDocument[]>(this.documentUrl);
    }

    public achangeApproved(document: MyDocument){
        return this.http.post<MyDocument>(this.documentUrl + 'changeApproved',document);                 
                    
      }

}