import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Timetable } from 'src/app/models/timetable.model';

@Component({
  selector: 'app-my-dialog-timetable',
  templateUrl: './my-dialog-timetable.component.html',
  styleUrls: ['./my-dialog-timetable.component.css']
})
export class MyDialogTimetableComponent implements OnInit {

  timetable: Timetable;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private dialogRef: MatDialogRef<any>) { }

  ngOnInit() {
    this.timetable = this.data.timetable;
  }

}
