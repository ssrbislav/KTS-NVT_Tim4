import { Component, OnInit } from '@angular/core';
import { PricelistService } from 'src/app/services/pricelist.service';
import { Pricelist } from 'src/app/models/pricelist.model';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { PricelistAddComponent } from './pricelist-add/pricelist-add.component';

@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css']
})
export class PricelistComponent implements OnInit {

  pricelists: Pricelist[];
  pricelist: Pricelist;

  constructor(private pricelistService: PricelistService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadAllPricelists();
  }  

  loadAllPricelists() {
    this.pricelistService.getPriclists().subscribe(
      data => {
        this.pricelists = data;
      }
    )
  }

  addPricelist() {

    const dialogConfig = new MatDialogConfig();

      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
      added: false
    };

    const dialogRef = this.dialog.open(PricelistAddComponent, dialogConfig);

  }

}
