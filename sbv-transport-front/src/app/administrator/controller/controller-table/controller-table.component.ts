import { Component, OnInit, OnChanges } from '@angular/core';
import { Controller } from 'src/app/models/controller.model';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-controller-table',
  templateUrl: './controller-table.component.html',
  styleUrls: ['./controller-table.component.css']
})
export class ControllerTableComponent implements OnInit{

  controllers: Controller[];

  constructor(private controllerService: ControllerService) { }

  ngOnInit() {
    this.loadAllControllers();
  }

  loadAllControllers(){
    this.controllerService.getControllers()
      .subscribe( data => {
        this.controllers = data;
      });
  }

  deleteController(id: BigInteger){
    console.log(id);
    this.controllerService.deleteController(id)
      .subscribe( data => {
        if(data == true){
          alert("Controller is deleted!");
          this.loadAllControllers();
        }else{
          alert("Something went wrong!");
        }
      });
  }

}
