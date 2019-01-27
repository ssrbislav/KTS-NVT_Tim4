import { Component, OnInit,Inject, Input,Output ,EventEmitter} from '@angular/core';
import { LineService } from 'src/app/services/line.service';
import { Line } from 'src/app/models/line.model';
import { SubwayService } from 'src/app/services/subway.service';
import { Subway } from 'src/app/models/subway.model';
import { TimetableService } from 'src/app/services/timetable.service';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { AltTimetableDTO } from 'src/app/models.dto/timetable.dto';
import { FilterSearchLineDTO } from 'src/app/models.dto/filterSearchLine.dto';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { Timetable } from 'src/app/models/timetable.model';
import { Schedule } from 'src/app/models/schedule.model';

@Component({
  selector: 'app-subway-view',
  templateUrl: './subway-view.component.html',
  styleUrls: ['./subway-view.component.css']
})
export class SubwayViewComponent implements OnInit {

  show: string = 'subway';
  showMap: boolean = true;
  lineSelected: Line = new Line();
  lineGot: Line;
  lines: Line[] = [];
  allLines: Line[] = [];
  timetables: Timetable[];
  result: Timetable[] = [];
  map: any;
  schedule: Set<Schedule>;
  times: Set<Date>;
  @Input() timeTable: any;
  @Output() filter = new EventEmitter<Timetable[]>();
  productForm: FormGroup;
  filterSearch: Line;
  id: BigInteger;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private lineService: LineService, private subwayService: SubwayService,
  private timetableService: TimetableService, private fb: FormBuilder) { }

  ngOnInit() {
    this.loadLines();
    // this.dialogRef.updateSize('80%', '80%');
  }

  loadFilterSearch(){
    console.log(this.filterSearch);
    this.lineService.getLine(this.filterSearch.id).subscribe(data => this.lineGot = data);
    this.loadTimetables(this.filterSearch);
    // console.log(this.lineGot)
    // this.loadTimetables(this.filterSearch);
  }

  resetSearch(){
    this.filterSearch = null;
  }

  loadLines(){
    this.lineService.getLines()
      .subscribe( data => {
        this.lines = data;
        this.loadSubwayLines();
      });
  }

  loadAlllines(){
    this.lineService.getLines()
      .subscribe( data => {
        this.allLines = data;
        this.loadSubwayLines();
      });

  }

  loadSubwayLines(){

    for (var i = 0; i < this.allLines.length; i++){
      if(this.allLines[i].line_type.toString() == "subway"){
        this.lines.push(this.allLines[i]);
      }
    }

  }

  loadTimetables(line: Line) {
    console.log(line);
    console.log(line.name);
    console.log(line.timetable);
    this.timetables = line.timetable;
    for (var j = 0; j < line.timetable.length; j ++) {
      this.schedule = line.timetable[j].schedule;
      for (var i = 0; i < line.timetable[j].schedule.size; i++) {
        this.times.add(line.timetable[j].schedule[i].times);
      }
    }
  }

  buyTicket() {
    
  }

}
