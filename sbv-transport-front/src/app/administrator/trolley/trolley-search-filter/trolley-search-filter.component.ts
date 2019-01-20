import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FilterSearchDTO } from 'src/app/models.dto/filterSearch.dto';
import { Line } from 'src/app/models/line.model';
import { Station } from 'src/app/models/station.model';
import { Trolley } from 'src/app/models/trolley.model';
import { LineService } from 'src/app/services/line.service';
import { TrolleyService } from 'src/app/services/trolley.service';
import { StationService } from 'src/app/services/station.service';

@Component({
  selector: 'app-trolley-search-filter',
  templateUrl: './trolley-search-filter.component.html',
  styleUrls: ['./trolley-search-filter.component.css']
})
export class TrolleySearchFilterComponent implements OnInit {

  filterSearch: FilterSearchDTO = new FilterSearchDTO(null,false,null,"");
  lines: Line[];
  stations: Station[];
  result: Trolley[];
  @Input() trolleyTable: any;
  @Output() filter = new EventEmitter<Trolley[]>();

  constructor(private lineService: LineService, private trolleyService: TrolleyService, private stationService: StationService) { }

  ngOnInit() {
    this.loadLines();
    this.loadStations();

  }

  loadLines(){
    this.lineService.getLines()
      .subscribe( data => {
        this.lines = data;
      });
  }

  loadStations(){
    this.stationService.getStations()
      .subscribe( data => {
        this.stations = data;
      });

  }

  loadFilterSearch(){
    
    this.trolleyService.filterSearch(this.filterSearch)
      .subscribe( data => {
        this.result = data;
        this.trolleyTable.loadSearchFilter(this.result);
        this.filter.emit(this.result);
      });

  }
}
