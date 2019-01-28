import { Component, OnInit, Inject } from '@angular/core';
import { Pricelist } from 'src/app/models/pricelist.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { PricelistService } from 'src/app/services/pricelist.service';

@Component({
  selector: 'app-pricelist-add',
  templateUrl: './pricelist-add.component.html',
  styleUrls: ['./pricelist-add.component.css']
})
export class PricelistAddComponent implements OnInit {

  pricelist: Pricelist = new Pricelist();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private pricelistService: PricelistService) { }

  ngOnInit() {
  }

  close() {
    this.dialogRef.close();
  }

  addPricelist(){
    this.pricelistService.addPricelist(this.pricelist).subscribe(
      data => {
        if(data != null) {
          alert("Pricelist created!");
        } else{
          alert("Something went wrong!");
        }
      }
    )
  }

}
