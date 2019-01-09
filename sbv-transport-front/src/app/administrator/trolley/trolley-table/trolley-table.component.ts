import { Component, OnInit } from '@angular/core';
import { Trolley } from 'src/app/models/trolley.model';
import { TrolleyService } from 'src/app/services/trolley.service';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { Line } from 'src/app/models/line.model';
import { MyDialogComponent } from '../../my-dialog-line/my-dialog-line.component';

@Component({
  selector: 'app-trolley-table',
  templateUrl: './trolley-table.component.html',
  styleUrls: ['./trolley-table.component.css']
})
export class TrolleyTableComponent implements OnInit {

  trolleys: Trolley[];

  constructor(private trolleyService: TrolleyService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadAllTrolleys();
  }

  loadAllTrolleys(){
    this.trolleyService.getTrolley()
    .subscribe( data => {
      this.trolleys = data;
    });
  }

  deleteTrolley(id: BigInteger){
    console.log(id);
    this.trolleyService.deleteTrolley(id)
      .subscribe( data => {
        if(data == true){
          alert("Trolley is deleted!");
          this.loadAllTrolleys();
        }else{
          alert("Something went wrong!");
        }
      });
  }

  openModal(line: Line) {
    const dialogConfig = new MatDialogConfig();

   dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
    id: 1,
    title: "Bojana",
    line: line
    };

   const dialogRef = this.dialog.open(MyDialogComponent, dialogConfig);

   dialogRef.afterClosed().subscribe(result => {
   console.log("Dialog was closed")
   console.log(result)

   });
    }

}
