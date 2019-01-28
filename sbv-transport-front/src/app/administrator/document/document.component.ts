import { Component, OnInit } from '@angular/core';
import { DocumentService } from 'src/app/services/document.service';
import { MyDocument } from 'src/app/models/document.model';
import { Passenger } from 'src/app/models/passenger.model';
import { PassengerService } from 'src/app/services/passenger.service';

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit {

  documents: MyDocument[] = [];
  passengers: Passenger[]= [];

  constructor(private documentService: DocumentService, private passengerService: PassengerService) { }

  ngOnInit() {
    this.loadDocuments();
  }

  loadDocuments(){
    this.documentService.getDocuments()
    .subscribe(data =>{
      this.documents = data;
      this.loadPassenger();
    })
  }

  loadPassenger(){
    this.passengerService.getPassengers()
    .subscribe(data =>{
      this.passengers = data;
    })
  }

  clickApproved(document: MyDocument){
    document.approved = "approved";
    this.documentService.achangeApproved(document)
    .subscribe(data =>{
      alert("Successfully approved!");
      this.loadDocuments();
    })
  }

  clickDecline(document: MyDocument){
    document.approved = "declined";
    this.documentService.achangeApproved(document)
    .subscribe(data =>{
      alert("Successfully declined!");
      this.loadDocuments();
    })
  }


}
