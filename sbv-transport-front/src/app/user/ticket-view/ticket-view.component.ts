import { Component, OnInit, Inject } from '@angular/core';
import { Ticket } from 'src/app/models/ticket.model';
import { PassengerService } from 'src/app/services/passenger.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TicketService } from 'src/app/services/ticket.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Passenger } from 'src/app/models/passenger.model';

@Component({
  selector: 'app-ticket-view',
  templateUrl: './ticket-view.component.html',
  styleUrls: ['./ticket-view.component.css']
})
export class TicketViewComponent implements OnInit {

  tickets: Ticket[] = [];
  passenger: Passenger;


  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private ticketService: TicketService, private passengerService: PassengerService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.loadTickets();
    console.log(this.tickets);
  }

  loadTickets() {
    let username = this.tokenStorage.getUsername();
    this.passengerService.getPassenger(username).subscribe(
      data => {
        // console.log(data);
        this.passenger = data;
        console.log(this.passenger);
        console.log(this.passenger.tickets.length);
        for (var i = 0; i < data.tickets.length; i++) {
          console.log(data.tickets);
          this.tickets.push(data.tickets[i]);
        }
      });
  }

  activate(id: BigInteger) {
    this.ticketService.activate(id).subscribe(
      data => {
        console.log(data);
        if (data.active == true) {
          alert("Activation was successful!");
        }
      }
    )
  }

}
