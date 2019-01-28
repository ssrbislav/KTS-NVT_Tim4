import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { PassengerService } from 'src/app/services/passenger.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Passenger } from 'src/app/models/passenger.model';
import { DocumentService } from 'src/app/services/document.service';
import { MyDocument } from 'src/app/models/document.model';

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.css']
})
export class ProfileViewComponent implements OnInit {

  passenger: Passenger = new Passenger();
  show: string = 'profil';
  passRepeat: string;
  @ViewChild('inputEl1') public inputEl1: ElementRef;
  @ViewChild('inputEl2') public inputEl2: ElementRef;
  @ViewChild('inputEl3') public inputEl3: ElementRef;
  @ViewChild('inputEl4') public inputEl4: ElementRef;
  @ViewChild('inputEl5') public inputEl5: ElementRef;
  @ViewChild('inputEl6') public inputEl6: ElementRef;
  @ViewChild('repeat') public repeat: ElementRef;


  document: string = 'not';
  doc: MyDocument;

  constructor(private passengerService: PassengerService, private tokenStorage: TokenStorageService,
    private documentService: DocumentService) { }

  ngOnInit() {
    this.findPassenger();
  }

  findPassenger() {
    let username = this.tokenStorage.getUsername();
    this.passengerService.getPassenger(username).subscribe(
      data => {
        this.passenger = data;
        this.passenger.id = data.id;
        this.checkDocument(true);
        //this.saveClicked();

      });
  }

 checkDocument(b:boolean){
  if(this.passenger.document!= null){
    this.doc = this.passenger.document;
    this.document = 'exist';
  }
 }  
  

  clickChange(){
    this.show = 'change';
    this.inputEl1.nativeElement.disabled = false;
    // this.inputEl2.nativeElement.disabled = false;
    this.inputEl3.nativeElement.disabled = false;
    this.inputEl4.nativeElement.disabled = false;
    this.inputEl5.nativeElement.disabled = false;
    this.inputEl6.nativeElement.disabled = false;

  }
  cancelClicked(){
    this.show = 'profil';
    this.inputEl1.nativeElement.disabled = true;
    // this.inputEl2.nativeElement.disabled = true;
    this.inputEl3.nativeElement.disabled = true;
    this.inputEl4.nativeElement.disabled = true;
    this.inputEl5.nativeElement.disabled = true;
    this.inputEl6.nativeElement.disabled = true;

  }

  saveClicked(){

    if(this.passenger.password == this.passRepeat){
      console.log(this.passenger);
      this.passengerService.updatePassenger(this.passenger, this.passenger.id)
      .subscribe( upData => {
        this.passenger = upData;
        console.log(upData);
        alert("Successfully profil change!");
        this.inputEl1.nativeElement.disabled = true;
        // this.inputEl2.nativeElement.disabled = true;
        this.inputEl3.nativeElement.disabled = true;
        this.inputEl4.nativeElement.disabled = true;
        this.inputEl5.nativeElement.disabled = true;
        this.inputEl6.nativeElement.disabled = true;
        this.show = 'profil';

      });
    }else{
      alert("Passwords didn't match!");
    }
  };

}
