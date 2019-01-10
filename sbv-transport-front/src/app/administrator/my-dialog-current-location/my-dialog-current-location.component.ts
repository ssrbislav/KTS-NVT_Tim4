import { Component, OnInit ,Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';


@Component({
  selector: 'app-my-dialog-current-location',
  templateUrl: './my-dialog-current-location.component.html',
  styleUrls: ['./my-dialog-current-location.component.css']
})
export class MyDialogCurrentLocationComponent implements OnInit {

  location: Location;
  show :string = 'exist';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>) {
    this.location = data.location;
   }

  ngOnInit() {
    this.loadPage();
  }

  loadPage(){
    if(this.location == null){
      this.show = 'not';
    }
  }

}
