import { Component, OnInit ,Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  // @ViewChild('usernameInput') usernameInputRef: ElementRef;
  // @ViewChild('passwordInput') passwordInputRef: ElementRef;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private dialogRef: MatDialogRef<any>) {
  }

  ngOnInit() {
    
  }

  

}
