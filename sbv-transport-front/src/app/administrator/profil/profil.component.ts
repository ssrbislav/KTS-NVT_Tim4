import { Component, OnInit, ViewChild,ElementRef } from '@angular/core';
import { Administrator } from 'src/app/models/administrator.model';
import { AdministratorService } from 'src/app/services/administrator.service';
import { discoverLocalRefs } from '@angular/core/src/render3/context_discovery';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {

  admin: Administrator = new Administrator();
  show:string = 'profil';
  passRepeat: string;
  @ViewChild('inputEl') public inputEl: ElementRef;
  @ViewChild('inputEl2') public inputEl2: ElementRef;
  @ViewChild('inputEl3') public inputEl3: ElementRef;

  constructor(private adminService: AdministratorService) { }

  ngOnInit() {
    this.findAdmin();
  }

  findAdmin(){
    console.log("Uslo!")
    this.adminService.findAdmin(1)
      .subscribe( data => {
        this.admin = data;
      });

  }

  clickChange(){
    this.show = 'change';
    this.inputEl.nativeElement.disabled = false;
    this.inputEl2.nativeElement.disabled = false;

  }
  cancelClicked(){
    this.show = 'profil';
    this.inputEl.nativeElement.disabled = true;
    this.inputEl2.nativeElement.disabled = true;

  }

  saveClicked(){

    this.admin.password = this.inputEl2.nativeElement.value;
    this.passRepeat = this.inputEl3.nativeElement.value;
    if(this.admin.password == this.passRepeat){
      this.adminService.updateAdministrator(this.admin)
      .subscribe( data => {
        this.admin = data;
        alert("Successfully profil change!");
        this.inputEl.nativeElement.disabled = true;
        this.inputEl2.nativeElement.disabled = true;
        this.show = 'profil';

      });
    }else{
      alert("Passwords didn't match!");
    }

  }

}
