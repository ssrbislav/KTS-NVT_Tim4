import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FilterSearchDTO } from 'src/app/models.dto/filterSearch.dto';
import { Line } from 'src/app/models/line.model';
import { Station } from 'src/app/models/station.model';
import { Subway } from 'src/app/models/subway.model';
import { LineService } from 'src/app/services/line.service';
import { SubwayService } from 'src/app/services/subway.service';
import { StationService } from 'src/app/services/station.service';

@Component({
  selector: 'app-subway-search-filter',
  templateUrl: './subway-search-filter.component.html',
  styleUrls: ['./subway-search-filter.component.css']
})
export class SubwaySearchFilterComponent implements OnInit {

  filterSearch: FilterSearchDTO = new FilterSearchDTO(null,false,null,"");
  lines: Line[];
  stations: Station[];
  result: Subway[];
  @Input() subwayTable: any;
  @Output() filter = new EventEmitter<Subway[]>();

  constructor(private lineService: LineService, private subwayService: SubwayService, private stationService: StationService) { }

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
    
    this.subwayService.filterSearch(this.filterSearch)
      .subscribe( data => {
        this.result = data;
        this.subwayTable.loadSearchFilter(this.result);
        this.filter.emit(this.result);
      });

  }

}
