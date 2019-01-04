import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() featureSelected = new EventEmitter<string>();

  constructor(private router: Router) { }

  ngOnInit() {
  }

  clickButtonLogin() {
    this.featureSelected.emit('login');
}

  clickButtonRegistration(){
    this.router.navigateByUrl('/registration');
  }


}
