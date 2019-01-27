import { Component, OnInit} from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { RegistrationComponent } from '../registration/registration.component';
import { MatDialogConfig, MatDialog } from '@angular/material';

@Component({
  selector: 'app-main-page',
  templateUrl: './mainPage.component.html',
  styleUrls: ['./mainPage.component.css']
})
export class MainPageComponent implements OnInit {

  constructor(public dialog: MatDialog){

  }

  ngOnInit() {
  }

  showPopUp : string;

  onNavigate(feature: string){
    this.showPopUp = feature;
    console.log(feature);
    if(feature == 'login'){
      this.showLogin();
    } else if (feature == 'signup') {
      this.showRegistration();
    }
  }

  showRegistration() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 2,
      title: "Bojana"
      };

    const dialogRef = this.dialog.open(RegistrationComponent, dialogConfig);

  }

  showLogin(){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      id: 1,
      title: "Bojana"
      };

    const dialogRef = this.dialog.open(LoginComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
    console.log("Dialog was closed")
    console.log(result)

    });

  }

  clickPicture(){
    alert("Please login/register so you can see more information!");
  }
  
 

}
