import { Component, OnInit ,ViewChild} from '@angular/core';
import { LoginComponent } from '../header/login/login.component';
import { RegistrationComponent } from '../header/registration/registration.component';
import { MatDialogConfig, MatDialog } from '@angular/material';

@Component({
  selector: 'app-main-page',
  templateUrl: './mainPage.component.html',
  styleUrls: ['./mainPage.component.css']
})
export class MainPageComponent implements OnInit {

  @ViewChild("registration") registration: RegistrationComponent;

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
    }
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
  
 

}
