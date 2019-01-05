import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { ControllerService } from 'src/app/services/controller.service';
import { Controller } from 'src/app/models/controller.model';

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

  constructor(private route: Router,private controllerService: ControllerService) { }

  ngOnInit() {
  }

  cancelClicked(){

    this.route.navigateByUrl('/administrator')

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


      const newController = new Controller(email,username,pass1,firstName,lastName,address2,phone,date);

      this.controllerService.addController(newController)
      .subscribe( data => {
          alert("Successfully controller added!");
          this.route.navigateByUrl('/administrator')

      });


    }else{
      alert("Passwords didn't match!");
    }

    
  }

}
