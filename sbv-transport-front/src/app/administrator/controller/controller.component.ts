import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-controller',
  templateUrl: './controller.component.html',
  styleUrls: ['./controller.component.css']
})
export class ControllerComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }

  addController(){
    this.router.navigateByUrl('/addController');

  }

}
