import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() featureSelected = new EventEmitter<string>();

  showView: string = 'notregister';

  constructor(private router: Router) { }

  ngOnInit() {
  }

  clickButtonLogin() {
    this.featureSelected.emit('login');
}

  clickButtonRegistration(){
    this.router.navigateByUrl('/registration');
  }

  administratorView(){
    this.showView = 'administrator';
  }

  clickBus(){
    this.featureSelected.emit('bus');
    
  }

  clickSubway(){
    this.featureSelected.emit('subway');

  }

  clickTrolley(){
    this.featureSelected.emit('trolley');
  }

  clickController(){
    this.featureSelected.emit('controller');

  }


}
