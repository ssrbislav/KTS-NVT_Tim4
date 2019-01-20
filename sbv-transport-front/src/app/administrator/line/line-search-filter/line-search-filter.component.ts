import { Component, OnInit, Input } from '@angular/core';
import { FilterSearchLineDTO } from 'src/app/models.dto/filterSearchLine.dto';
import { Station } from 'src/app/models/station.model';
import { Line } from 'src/app/models/line.model';
import { LineService } from 'src/app/services/line.service';
import { StationService } from 'src/app/services/station.service';

@Component({
  selector: 'app-line-search-filter',
  templateUrl: './line-search-filter.component.html',
  styleUrls: ['./line-search-filter.component.css']
})
export class LineSearchFilterComponent implements OnInit {

  filterSearch: FilterSearchLineDTO = new FilterSearchLineDTO("","",null,"");
  stations: Station[];
  result: Line[];
  @Input() lineTable: any;

  constructor(private lineService: LineService, private stationService: StationService) { }

  ngOnInit() {
    this.loadStations();

  }

  loadStations(){
    this.stationService.getStations()
      .subscribe( data => {
        this.stations = data;
      });

  }

  loadFilterSearch(){
    
    this.lineService.filterSearch(this.filterSearch)
      .subscribe( data => {
        this.result = data;
        this.lineTable.loadSearchFilter(this.result);
      });

    }

  resetSearch(){
    this.filterSearch = new FilterSearchLineDTO("","",null,"");
  }

}
