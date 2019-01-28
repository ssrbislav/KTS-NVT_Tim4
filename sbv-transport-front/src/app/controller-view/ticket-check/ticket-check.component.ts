import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-ticket-check',
  templateUrl: './ticket-check.component.html',
  styleUrls: ['./ticket-check.component.css']
})
export class TicketCheckComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
            private controllerService: ControllerService) { }

  ngOnInit() { }

  ticketCheck(id: BigInteger) {
    this.controllerService.checkTicket(id).subscribe(
      data => {
        if(data == true) {
          alert("Ticket is valid!");
          //return true;
        }
        else {
          alert("Ticket is not valid");
          //return false;
        }
      });
  }

  

}
