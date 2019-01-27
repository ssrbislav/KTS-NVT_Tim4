import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ticket } from '../models/ticket.model';
import { BuyTicketDTO } from '../models.dto/buyTicket.dto';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=UTF-8'})
  };

@Injectable({providedIn: 'root'})
export class TicketService{

  constructor(private http:HttpClient) {}

  private ticketUrl = 'http://localhost:8080/api/ticket/';

  public getTickets() {
      return this.http.get<Ticket[]>(this.ticketUrl);
    }
      
  public deleteTicket(id: BigInteger){
    const url = `${this.ticketUrl + 'deleteTicket'}/${id}`;
    return this.http.get<boolean>(url);

  }

  public addTicket(ticket: BuyTicketDTO){
    return this.http.post<Ticket>(this.ticketUrl + 'addTicket',ticket);                 
                
  }

  public findByUserID(id: BigInteger){
    const url = `${this.ticketUrl + 'findByUserID'}/${id}`;
    return this.http.get<Ticket[]>(url);                    
  }

  public activate(id: BigInteger) {
    const url = `${this.ticketUrl + 'activate'}/${id}`;
    return this.http.post<Ticket>(url, null);  
  }

//   public updateTicket(ticket: ChangeTransportDTO){
//     return this.http.post<Ticket>(this.ticketUrl + 'updateTicket',ticket,httpOptions);                 
                
//   }

//   public filterSearch(values: FilterSearchTransportDTO){
//     return this.http.post<Ticket[]>(this.ticketUrl + 'searchFilter',values);                 
                
//   }


  }