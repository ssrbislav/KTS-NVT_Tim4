import { Injectable } from '@angular/core';
import { ReportTicketDTO } from '../models.dto/reportTicket.dto';
import { ReportResultTicketDTO } from '../models.dto/reportResultTicket.dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Pricelist } from '../models/pricelist.model';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=UTF-8'})
  };

@Injectable()
export class PricelistService{

    constructor(private http:HttpClient) {}

    pricelistUrl = 'http://localhost:8080/api/pricelist/';

    public reportTicket(pricelist: ReportTicketDTO){
        return this.http.post<ReportResultTicketDTO[]>(this.pricelistUrl + 'reportTicket',pricelist);                        
    }

    public getPriclists() {
        return this.http.get<Pricelist[]>(this.pricelistUrl);
    }

    public addPricelist(pricelist: Pricelist) {
        return this.http.post<Pricelist>(this.pricelistUrl + 'addPricelist', pricelist);
    }

    public updatePricelist(pricelist: Pricelist) {
        return this.http.post<Pricelist>(this.pricelistUrl + 'updatePricelist', pricelist, httpOptions);
    }
    
}