import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './header/login/login.component';
import { HeaderComponent } from './header/header.component';
import {PopupModule} from 'ng2-opd-popup';
import { RegistrationComponent } from './header/registration/registration.component';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './mainPage/mainPage.component';
import { AdministratorComponent } from './administrator/administrator.component';
import { BusComponent } from './administrator/bus/bus.component';
import { BusTableComponent } from './administrator/bus/bus-table/bus-table.component';
import { BusService } from './services/bus.service';
import { HttpClientModule } from '@angular/common/http';
import { SubwayComponent } from './administrator/subway/subway.component';
import { SubwayTableComponent } from './administrator/subway/subway-table/subway-table.component';
import { SubwayService } from './services/subway.service';
import { TrolleyComponent } from './administrator/trolley/trolley.component';
import { TrolleyTableComponent } from './administrator/trolley/trolley-table/trolley-table.component';
import { TrolleyService } from './services/trolley.service';
import { ControllerComponent } from './administrator/controller/controller.component';
import { ControllerTableComponent } from './administrator/controller/controller-table/controller-table.component';
import { ControllerService } from './services/controller.service';
import { ControllerAddComponent } from './administrator/controller/controller-add/controller-add.component';
import { ProfilComponent } from './administrator/profil/profil.component';
import { AdministratorService } from './services/administrator.service';

const appRoutes: Routes = [
  { path: 'registration', component: RegistrationComponent },
  { path: 'mainPage', component: MainPageComponent },
  { path: '',
    redirectTo: '/mainPage',
    pathMatch: 'full'
  },
  { path: 'administrator', component: AdministratorComponent},
  {path: 'addController', component: ControllerAddComponent},
];
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    RegistrationComponent,
    MainPageComponent,
    AdministratorComponent,
    BusComponent,
    BusTableComponent,
    SubwayComponent,
    SubwayTableComponent,
    TrolleyComponent,
    TrolleyTableComponent,
    ControllerComponent,
    ControllerTableComponent,
    ControllerAddComponent,
    ProfilComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    PopupModule.forRoot(),
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [BusService,SubwayService,TrolleyService,ControllerService, AdministratorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
