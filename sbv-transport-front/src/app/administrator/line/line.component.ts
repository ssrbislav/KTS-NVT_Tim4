import { Component, OnInit ,ViewChild} from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { LineAddComponent } from './line-add/line-add.component';
import { LineTableComponent } from './line-table/line-table.component';

@Component({
  selector: 'app-line',
  templateUrl: './line.component.html',
  styleUrls: ['./line.component.css']
})
export class LineComponent implements OnInit {

  @ViewChild("lineTable") table: LineTableComponent;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  addLine(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana"
      };

    const dialogRef = this.dialog.open(LineAddComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)
    this.table.loadAllLines();
  

    });


  }

}
