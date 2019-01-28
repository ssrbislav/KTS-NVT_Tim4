import { Component, OnInit } from '@angular/core';
import { TicketService } from 'src/app/services/ticket.service';
import { Ticket } from 'src/app/models/ticket.model';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css']
})
export class TicketsComponent implements OnInit {

  tickets: Ticket[];
  ticket: Ticket;

  constructor(private ticketService: TicketService, private controllerService: ControllerService) { }

  ngOnInit() {
    this.loadAllTickets();
  }

  loadAllTickets(){
    this.ticketService.getTickets().subscribe(
      data => {
        this.tickets = data;
      });
  }

  

  blockTicket(id: BigInteger) {
    this.ticketService.getOne(id).subscribe(
      data => {
        this.ticket = data;
      });

    if(this.ticket.block) {
      alert("Ticket already blocked!");
      return;
    }

    this.controllerService.blockTicket(id).subscribe(
      data => {
        if(data == true) {
          alert("Ticket successfully blocked!");
          this.loadAllTickets();
        } else {
          alert("Something went wrong!");
        }
    });
  }

  unblockTicket(id: BigInteger) {
/*
    this.ticketService.getOne(id).subscribe(
      data => {
        this.ticket = data;
      });

    if(!this.ticket.block) {
      alert("Ticket is not blocked!");
      return;
    }
    */

    this.controllerService.unblockTicket(id).subscribe(
      data => {
        if(data == true) {
          alert("Ticket successfully unblocked!");
          this.loadAllTickets();
        } else {
          alert("Something went wrong!");
        }
      });
  }
  

}
