import { Component, OnInit } from '@angular/core';
import { TicketService } from 'src/app/services/ticket.service';
import { Ticket } from 'src/app/models/ticket.model';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css']
})
export class TicketsComponent implements OnInit {

  tickets: Ticket[];

  constructor(private ticketService: TicketService) { }

  ngOnInit() {
    this.loadAllTickets();
  }

  loadAllTickets(){
    this.ticketService.getTickets().subscribe(
      data => {
        this.tickets = data;
      });
  }

  

}
