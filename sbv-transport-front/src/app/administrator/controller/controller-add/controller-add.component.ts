import { Component, OnInit, ViewChild, ElementRef ,Inject} from '@angular/core';
import { Router } from '@angular/router';
import { ControllerService } from 'src/app/services/controller.service';
import { Controller } from 'src/app/models/controller.model';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-controller-add',
  templateUrl: './controller-add.component.html',
  styleUrls: ['./controller-add.component.css']
})
export class ControllerAddComponent implements OnInit {

  @ViewChild('firstNameInput') firstNameInputRef: ElementRef;
  @ViewChild('lastNameInput') lastNameInputRef: ElementRef;
  @ViewChild('emailInput') emailInputRef: ElementRef;
  @ViewChild('usernameInput') usernameInputRef: ElementRef;
  @ViewChild('pass1Input') pass1InputRef: ElementRef;
  @ViewChild('pass2Input') pass2InputRef: ElementRef;
  @ViewChild('addressInput') addressInputRef: ElementRef;
  @ViewChild('phoneInput') phoneInputRef: ElementRef;
  @ViewChild('dateInput') dateInputRef: ElementRef;

  controller: Controller;
  passwordRepeat: string;
  errorMessage = '';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,private route: Router
  ,private controllerService: ControllerService) {
    console.log(data)
    }

  ngOnInit() {
  }

  checkSame(pass: string) {

    const pass1 = this.pass1InputRef.nativeElement.value;

    this.passwordRepeat = pass;
    if (this.passwordRepeat !== pass1) {
      this.errorMessage = "Passwords do not match!"
    }
    else {
      this.errorMessage = "";
    }
  }

  addController(){

    const pass1 = this.pass1InputRef.nativeElement.value;
    const pass2 = this.pass2InputRef.nativeElement.value;

    if(pass1== pass2){
      const firstName = this.firstNameInputRef.nativeElement.value;
      const lastName = this.lastNameInputRef.nativeElement.value;
      const email = this.emailInputRef.nativeElement.value;
      const username = this.usernameInputRef.nativeElement.value;
      const address2 = this.addressInputRef.nativeElement.value;
      const phone = this.phoneInputRef.nativeElement.value;
      const date = this.dateInputRef.nativeElement.value;

      console.log("Ime: " + firstName);
      console.log("Prezime: " + lastName);

      console.log("Email: " + email);
      console.log("Username: " + username);
      console.log("adresa: " + address2);
      console.log("phone: " + phone);
      console.log("date: " + date);

      const newController = new Controller();
      newController.username = username;
      newController.email = email;
      newController.password = pass1;
      newController.first_name = firstName;
      newController.last_name = lastName;
      newController.address = address2;
      newController.phone_number = phone;
      newController.date_birth = date;
      newController.roles = ['controller'];
      //alert(newController.roles);

      this.controllerService.addController(newController)
      .subscribe( data => {
          alert("Successfully controller added!");
          this.dialogRef.close();  
      },error =>{
          alert("Username or email already exist!");

      });


    }else{
      alert("Passwords didn't match!");
    }

    
  }

}
