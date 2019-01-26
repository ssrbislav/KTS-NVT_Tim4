import { Component, OnInit } from '@angular/core';
import { DocumentService } from 'src/app/services/document.service';
import { MyDocument } from 'src/app/models/document.model';

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit {

  documents: MyDocument[] = [];

  constructor(private documentService: DocumentService) { }

  ngOnInit() {
    this.loadDocuments();
  }

  loadDocuments(){
    this.documentService.getDocuments()
    .subscribe(data =>{
      this.documents = data;
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
