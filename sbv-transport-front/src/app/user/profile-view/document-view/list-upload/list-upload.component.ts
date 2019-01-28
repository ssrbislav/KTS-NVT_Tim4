import { Component, OnInit } from '@angular/core';
import { PassengerService } from 'src/app/services/passenger.service';
import { DocumentService } from 'src/app/services/document.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Passenger } from 'src/app/models/passenger.model';
import { MyDocument } from 'src/app/models/document.model';

@Component({
  selector: 'app-list-upload',
  templateUrl: './list-upload.component.html',
  styleUrls: ['./list-upload.component.css']
})
export class ListUploadComponent implements OnInit {

  showFile = false;
  fileUpload: String;
  showPicture = false;
  document: MyDocument;
  passenger: Passenger = new Passenger();

  ImagePath: string = '';
 
  constructor(private passengerService: PassengerService, private documentService: DocumentService,
    private tokenStorage: TokenStorageService) {}
 
  ngOnInit() {
    this.findPassenger();
  }

  findPassenger() {
    let username = this.tokenStorage.getUsername();
    alert(username);
    this.passengerService.getPassenger(username).subscribe(
      data => {
        this.passenger = data;
      });
  }
 
  showFiles(enable: boolean) {
    this.showFile = enable
 
    if (enable) {
      alert(this.passenger.document.id);
      this.documentService.getOneDocument(this.passenger.document.id)
      .subscribe(data=>{
        this.document = data;
        if(this.document!= null){
          this.ImagePath = this.document.image_location;
          this.passengerService.getFiles(this.ImagePath)
          .subscribe(data=>{
            this.fileUpload = data;
    
          })
        }
      })
     
    }

    


  }

}
