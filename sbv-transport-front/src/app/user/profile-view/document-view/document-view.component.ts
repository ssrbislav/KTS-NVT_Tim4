import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-document-view',
  templateUrl: './document-view.component.html',
  styleUrls: ['./document-view.component.css']
})
export class DocumentViewComponent implements OnInit {

  @Output() nameEvent = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit() {
  }

  changeView(){
    this.nameEvent.emit(true);
  }

}
