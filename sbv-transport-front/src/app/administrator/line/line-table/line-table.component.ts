import { Component, OnInit } from '@angular/core';
import { LineService } from 'src/app/services/line.service';
import { Line } from 'src/app/models/line.model';

@Component({
  selector: 'app-line-table',
  templateUrl: './line-table.component.html',
  styleUrls: ['./line-table.component.css']
})
export class LineTableComponent implements OnInit {

  lines: Line[];

  constructor(private lineService: LineService) { }

  ngOnInit() {
    this.loadAllLines();
  }

  loadAllLines(){
    this.lineService.getLines()
      .subscribe( data => {
        this.lines = data;
      });
  }

}
