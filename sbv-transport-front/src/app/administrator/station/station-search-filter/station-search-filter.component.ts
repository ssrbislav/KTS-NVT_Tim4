import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { StationService } from 'src/app/services/station.service';
import { Station } from 'src/app/models/station.model';
import { FilterSearchStationDTO } from 'src/app/models.dto/filterSearchStation.dto';

@Component({
  selector: 'app-station-search-filter',
  templateUrl: './station-search-filter.component.html',
  styleUrls: ['./station-search-filter.component.css']
})
export class StationSearchFilterComponent implements OnInit {

  filterSearch: FilterSearchStationDTO = new FilterSearchStationDTO("","");
  result: Station[];
  @Input() appTable: any;
  @Output() filter = new EventEmitter<Station[]>();

  constructor(private stationService: StationService) { }

  ngOnInit() {
  }

  loadFilterSearch(){
    
    this.stationService.filterSearch(this.filterSearch)
      .subscribe( data => {
        this.result = data;
        this.appTable.loadSearchFilter(this.result);
        this.filter.emit(this.result);
      });

    }

  resetSearch(){
    this.filterSearch = new FilterSearchStationDTO("","");
  }


}
