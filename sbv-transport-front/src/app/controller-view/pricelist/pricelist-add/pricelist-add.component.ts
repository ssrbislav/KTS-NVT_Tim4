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

  errorMessage = '';
  pricelist: Pricelist = new Pricelist();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>,
  private pricelistService: PricelistService) { }

  ngOnInit() {
  }

  close() {
    this.dialogRef.close();
  }

  addPricelist(){

          this.pricelist.deleted = false;
          this.pricelist.active = true;

          if(this.pricelist.senior_discount_percentage > 100 || this.pricelist.senior_discount_percentage < 0 ||
            this.pricelist.student_discount_percentage > 100 || this.pricelist.student_discount_percentage < 0 ||
            this.pricelist.standard_discount_percentage > 100 || this.pricelist.standard_discount_percentage < 0 ||
            this.pricelist.double_zone_premium_percentage < 100) {
              alert("Data not correct!");
              return;
          } 

          this.pricelistService.addPricelist(this.pricelist).subscribe(
            data => {
              if(data != null) {
                alert("Pricelist created!");
                this.close();
              }
              else
                alert("Something went wrong!"); 
          });
        }

}
