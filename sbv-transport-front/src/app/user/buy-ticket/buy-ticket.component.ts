import { Component, OnInit, Inject, Input } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { BuyTicketDTO } from 'src/app/models.dto/buyTicket.dto';
import { TicketService } from 'src/app/services/ticket.service';
import { Ticket } from 'src/app/models/ticket.model';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { PassengerService } from 'src/app/services/passenger.service';
import { Passenger } from 'src/app/models/passenger.model';

@Component({
  selector: 'app-buy-ticket',
  templateUrl: './buy-ticket.component.html',
  styleUrls: ['./buy-ticket.component.css']
})
export class BuyTicketComponent implements OnInit {

  @Input() buyTicket: BuyTicketDTO = new BuyTicketDTO();
  passenger: Passenger;
  ticket: Ticket;
  form: any = {};

  constructor(@Inject (MAT_DIALOG_DATA) private data: any, @Inject(MAT_DIALOG_DATA) private ticketData: any, private dialogRef: MatDialogRef<any>, 
    private passengerService: PassengerService, private ticketService: TicketService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
  }

  addTicket() {
    let username = this.tokenStorage.getUsername();
    this.passengerService.getPassenger(username).subscribe(
      data => {
        // console.log(data);
        this.passenger = data;
        this.buyTicket.idPassenger = this.passenger.id;
        this.ticketService.addTicket(this.buyTicket).subscribe(
          ticketData => {
            // console.log(ticketData);
            if(ticketData!= null){
              this.ticket = ticketData;
              alert("Ticket successfully bought!");
              this.reloadPage();
            }else{
              alert("Something went wrong!");
            }
          });
        if (data == null) {
          console.log("Damn.");
        }});
  }

  reloadPage() {
    window.location.reload();
  }

}
