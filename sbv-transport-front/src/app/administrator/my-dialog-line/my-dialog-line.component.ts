import { Component, OnInit,Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { Line } from 'src/app/models/line.model';



@Component({
  selector: 'app-my-dialog',
  templateUrl: './my-dialog-line.component.html',
  styleUrls: ['./my-dialog-line.component.css']
})
export class MyDialogComponent implements OnInit {

  modalTitle: string;
  line: Line;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>) {
   this.modalTitle = data.title;
   this.line = data.line;
   console.log(data)
   }
   
  ngOnInit() {
    this.dialogRef.updateSize('80%', '80%');
  }

}
