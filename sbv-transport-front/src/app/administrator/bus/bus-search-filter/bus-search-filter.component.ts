import { Component, OnInit,Input,Output ,EventEmitter} from '@angular/core';
import { FilterSearchTransportDTO } from 'src/app/models.dto/filterSearchTransport.dto';
import { LineService } from 'src/app/services/line.service';
import { Line } from 'src/app/models/line.model';
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/models/bus.model';
import { Station } from 'src/app/models/station.model';
import { StationService } from 'src/app/services/station.service';

@Component({
  selector: 'app-bus-search-filter',
  templateUrl: './bus-search-filter.component.html',
  styleUrls: ['./bus-search-filter.component.css']
})
export class BusSearchFilterComponent implements OnInit {

  filterSearch: FilterSearchTransportDTO = new FilterSearchTransportDTO(null,false,null,"");
  lines: Line[];
  stations: Station[];
  result: Bus[];
  @Input() busTable: any;
  @Output() filter = new EventEmitter<Bus[]>();

  constructor(private lineService: LineService, private busService: BusService, private stationService: StationService) { }

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
    
    this.busService.filterSearch(this.filterSearch)
      .subscribe( data => {
        this.result = data;
        this.busTable.loadSearchFilter(this.result);
        this.filter.emit(this.result);
      });

    }

  resetSearch(){
    this.filterSearch = new FilterSearchTransportDTO(null,false,null,"");
  }

}
