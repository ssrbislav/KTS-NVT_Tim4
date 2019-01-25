import { Injectable } from '@angular/core';
import { ReportTicketDTO } from '../models.dto/reportTicket.dto';
import { ReportResultTicketDTO } from '../models.dto/reportResultTicket.dto';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class PricelistService{

    constructor(private http:HttpClient) {}

    private pricelistUrl = 'http://localhost:8080/api/pricelist/';

    public reportTicket(pricelist: ReportTicketDTO){
        return this.http.post<ReportResultTicketDTO[]>(this.pricelistUrl + 'reportTicket',pricelist);                 
                    
      }
    
}