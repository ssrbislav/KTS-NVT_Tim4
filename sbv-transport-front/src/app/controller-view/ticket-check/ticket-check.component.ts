import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-ticket-check',
  templateUrl: './ticket-check.component.html',
  styleUrls: ['./ticket-check.component.css']
})
export class TicketCheckComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>) { }

  ngOnInit() {
    //this.dialogRef.updateSize('80%', '80%'); 
  }

  ticketCheck(): boolean {
    return true;
  }

  

}
