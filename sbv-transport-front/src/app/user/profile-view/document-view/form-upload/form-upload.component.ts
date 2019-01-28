import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PassengerService } from 'src/app/services/passenger.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Passenger } from 'src/app/models/passenger.model';

@Component({
  selector: 'app-form-upload',
  templateUrl: './form-upload.component.html',
  styleUrls: ['./form-upload.component.css']
})
export class FormUploadComponent implements OnInit {

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  passenger: Passenger;
  @Output() added = new EventEmitter<boolean>();

 
  constructor(private passengerService:PassengerService, private tokenStorage: TokenStorageService) { }
 
  ngOnInit() {
    this.findPassenger();
  }

  findPassenger() {
    let username = this.tokenStorage.getUsername();
    this.passengerService.getPassenger(username).subscribe(
      data => {
        this.passenger = data;
      });
  }
 
  selectFile(event) {
    const file = event.target.files.item(0)
 
    if (file.type.match('image.*')) {
      this.selectedFiles = event.target.files;
    } else {
      alert('invalid format!');
    }
  }
 
  upload() {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0)
    this.passengerService.pushFileToStorage(this.currentFileUpload, this.passenger.id).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
        this.added.emit(true);
      }
      
    })
 
    this.selectedFiles = undefined;
  
   
  }

  
}
