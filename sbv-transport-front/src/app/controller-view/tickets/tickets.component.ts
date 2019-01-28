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
    this.controllerService.blockTicket(id).subscribe(
      data => {
        if(data == true) {
          alert("Ticket Blocked!");
          this.loadAllTickets();
        }
        alert("Something went wrong!");
    });
  }
  

}
