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
import { LineComponent } from './administrator/line/line.component';
import { StationComponent } from './administrator/station/station.component';
import { ReportComponent } from './administrator/report/report.component';
import { MyDialogComponent } from './administrator/my-dialog-line/my-dialog-line.component';
import { MatDialogModule, MatButtonModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MyDialogCurrentLocationComponent } from './administrator/my-dialog-current-location/my-dialog-current-location.component';
import { LineTableComponent } from './administrator/line/line-table/line-table.component';
import { LineService } from './services/line.service';
import { StationTableComponent } from './administrator/station/station-table/station-table.component';
import { StationService } from './services/station.service';
import { MyDialogFirstStationComponent } from './administrator/line/line-table/my-dialog-first-station/my-dialog-first-station.component';
import { MyDialogStationsComponent } from './administrator/line/line-table/my-dialog-stations/my-dialog-stations.component';
import { StationAddComponent } from './administrator/station/station-add/station-add.component';
import { LocationService } from './services/location.service';
import { FormsModule } from '@angular/forms';
import { LineAddComponent } from './administrator/line/line-add/line-add.component';


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
    ProfilComponent,
    LineComponent,
    StationComponent,
    ReportComponent,
    MyDialogComponent,
    MyDialogCurrentLocationComponent,
    LineTableComponent,
    StationTableComponent,
    MyDialogFirstStationComponent,
    MyDialogStationsComponent,
    StationAddComponent,
    LineAddComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    PopupModule.forRoot(),
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    MatDialogModule,
    MatButtonModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  entryComponents: [
    MyDialogComponent,
    MyDialogCurrentLocationComponent,
    MyDialogFirstStationComponent,
    MyDialogStationsComponent,
    StationAddComponent,
    LineAddComponent
   ],
  providers: [BusService,SubwayService,TrolleyService,ControllerService, AdministratorService,LineService,StationService, 
    LocationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
