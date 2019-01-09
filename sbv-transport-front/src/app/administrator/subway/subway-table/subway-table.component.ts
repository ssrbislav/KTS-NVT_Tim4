import { Component, OnInit } from '@angular/core';
import { Subway } from 'src/app/models/subway.model';
import { SubwayService } from 'src/app/services/subway.service';
import { Line } from 'src/app/models/line.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { MyDialogComponent } from '../../my-dialog-line/my-dialog-line.component';

@Component({
  selector: 'app-subway-table',
  templateUrl: './subway-table.component.html',
  styleUrls: ['./subway-table.component.css']
})
export class SubwayTableComponent implements OnInit {
  
  subways: Subway[];

  constructor(private subwayService: SubwayService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadAllSubways();
  }

  loadAllSubways(){
    this.subwayService.getSubways()
      .subscribe( data => {
        this.subways = data;
      });
  }

  deleteSubway(id: BigInteger){
    console.log(id);
    this.subwayService.deleteSubway(id)
      .subscribe( data => {
        if(data == true){
          alert("Subway is deleted!");
          this.loadAllSubways();
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
