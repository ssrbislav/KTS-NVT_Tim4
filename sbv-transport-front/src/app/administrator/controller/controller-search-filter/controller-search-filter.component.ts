import { Component, OnInit, Input } from '@angular/core';
import { FilterSearchControllerDTO } from 'src/app/models.dto/filterSearchController.dto';
import { Controller } from 'src/app/models/controller.model';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-controller-search-filter',
  templateUrl: './controller-search-filter.component.html',
  styleUrls: ['./controller-search-filter.component.css']
})
export class ControllerSearchFilterComponent implements OnInit {

  filterSearch: FilterSearchControllerDTO = new FilterSearchControllerDTO("","");
  result: Controller[];
  @Input() controllerTable: any;

  constructor(private controllerService: ControllerService) { }

  ngOnInit() {
  }

  loadFilterSearch(){
    this.controllerService.filterSearch(this.filterSearch)
      .subscribe( data => {
        this.result = data;
        this.controllerTable.loadSearchFilter(this.result);
      });

    }

  resetSearch(){
    this.filterSearch = new FilterSearchControllerDTO("","");
  }

}
