import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { RegistrationComponent } from './registration/registration.component';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './unregisteredHome/mainPage.component';
import { AdministratorComponent } from './administrator/administrator.component';
import { BusComponent } from './administrator/bus/bus.component';
import { BusTableComponent } from './administrator/bus/bus-table/bus-table.component';
import { BusService } from './services/bus.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
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
import { MatDialogModule, MatButtonModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LineAddComponent } from './administrator/line/line-add/line-add.component';
import { BusAddComponent } from './administrator/bus/bus-add/bus-add.component';
import { SubwayAddComponent } from './administrator/subway/subway-add/subway-add.component';
import { TrolleyAddComponent } from './administrator/trolley/trolley-add/trolley-add.component';
import { BusEditComponent } from './administrator/bus/bus-edit/bus-edit.component';
import { SubwayEditComponent } from './administrator/subway/subway-edit/subway-edit.component';
import { TrolleyEditComponent } from './administrator/trolley/trolley-edit/trolley-edit.component';
import { StationEditComponent } from './administrator/station/station-edit/station-edit.component';
import { LineEditComponent } from './administrator/line/line-edit/line-edit.component';
import { MyDialogTimetableComponent } from './administrator/my-dialog-timetable/my-dialog-timetable.component';
import { TimetableService } from './services/timetable.service';
import { BusSearchFilterComponent } from './administrator/bus/bus-search-filter/bus-search-filter.component';
import { SubwaySearchFilterComponent } from './administrator/subway/subway-search-filter/subway-search-filter.component';
import { TrolleySearchFilterComponent } from './administrator/trolley/trolley-search-filter/trolley-search-filter.component';
import { LineSearchFilterComponent } from './administrator/line/line-search-filter/line-search-filter.component';
import { StationSearchFilterComponent } from './administrator/station/station-search-filter/station-search-filter.component';
import { ControllerSearchFilterComponent } from './administrator/controller/controller-search-filter/controller-search-filter.component';
import { UserComponent } from './user/user.component';
import { BusViewComponent } from './user/bus-view/bus-view.component';
import { RoleGuardService as RoleGuard } from './auth/role-guard.service';
import { httpInterceptorProviders, AuthInterceptor  } from './auth/auth-interceptor';
import { SubwayViewComponent } from './user/subway-view/subway-view.component';
import { TrolleyViewComponent } from './user/trolley-view/trolley-view.component';
import { ControllerViewComponent } from './controller-view/controller-view.component';
import { PricelistService } from './services/pricelist.service';
import { TicketCheckComponent } from './controller-view/ticket-check/ticket-check.component';
import { PricelistComponent } from './controller-view/pricelist/pricelist.component';
import { DocumentComponent } from './administrator/document/document.component';
import { DocumentService } from './services/document.service';
import { LineTimetableComponent } from './administrator/line/line-table/line-timetable/line-timetable.component';
import { BuyTicketComponent } from './user/buy-ticket/buy-ticket.component';
import { TicketService } from './services/ticket.service';
import { PassengerService } from './services/passenger.service';
import { TicketViewComponent } from './user/ticket-view/ticket-view.component';
import { ProfileViewComponent } from './user/profile-view/profile-view.component';
import { HttpModule } from '@angular/http';
import { TicketsComponent } from './controller-view/tickets/tickets.component';
import { PricelistAddComponent } from './controller-view/pricelist/pricelist-add/pricelist-add.component';
import { PricelistUpdateComponent } from './controller-view/pricelist/pricelist-update/pricelist-update.component';
import { DocumentViewComponent } from './user/profile-view/document-view/document-view.component';
import { DetailsUploadComponent } from './user/profile-view/document-view/details-upload/details-upload.component';
import { FormUploadComponent } from './user/profile-view/document-view/form-upload/form-upload.component';
import { ListUploadComponent } from './user/profile-view/document-view/list-upload/list-upload.component';


const appRoutes: Routes = [
 
  { 
    path: 'mainPage', 
    component: MainPageComponent,
   
  },
  { 
    path: '',
    redirectTo: '/mainPage',
    pathMatch: 'full'
  },
  { 
    path: 'administrator', 
    component: AdministratorComponent,
    canActivate: [RoleGuard],
    data: {
      expectedRole : 'ROLE_ADMIN'
    }
  },
  { 
    path: 'user', 
    component: UserComponent,
    canActivate: [RoleGuard],
    data: {
      expectedRole : 'ROLE_PASSENGER'
    } 
  },
  {
    path: 'signup',
    component: RegistrationComponent, 
  },
  { 
    path: 'controller', 
    component: ControllerViewComponent,
    canActivate: [RoleGuard],
    data: {
      expectedRole : 'ROLE_CONTROLLER'
    }
  },
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
    LineAddComponent,
    BusAddComponent,
    SubwayAddComponent,
    TrolleyAddComponent,
    BusEditComponent,
    SubwayEditComponent,
    TrolleyEditComponent,
    StationEditComponent,
    LineEditComponent,
    MyDialogTimetableComponent,
    BusSearchFilterComponent,
    SubwaySearchFilterComponent,
    TrolleySearchFilterComponent,
    LineSearchFilterComponent,
    StationSearchFilterComponent,
    ControllerSearchFilterComponent,
    UserComponent,
    BusViewComponent,
    SubwayViewComponent,
    TrolleyViewComponent,
    ControllerViewComponent,
    TicketCheckComponent,
    PricelistComponent,
    DocumentComponent,
    BuyTicketComponent,
    TicketViewComponent,
    ProfileViewComponent,
    DocumentComponent,
    LineTimetableComponent,
    TicketsComponent,
    PricelistAddComponent,
    PricelistUpdateComponent,
    DocumentViewComponent,
    DetailsUploadComponent,
    FormUploadComponent,
    ListUploadComponent

  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    MatDialogModule,
    MatButtonModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule
  ],
  entryComponents: [
    MyDialogComponent,
    MyDialogCurrentLocationComponent,
    MyDialogFirstStationComponent,
    MyDialogStationsComponent,
    StationAddComponent,
    LineAddComponent,
    LoginComponent,
    BusAddComponent,
    SubwayAddComponent,
    TrolleyAddComponent,
    BusEditComponent,
    SubwayEditComponent,
    TrolleyEditComponent,
    StationEditComponent,
    LineEditComponent,
    MyDialogTimetableComponent,
    ControllerAddComponent,
    TicketCheckComponent,
    LineTimetableComponent,
    PricelistAddComponent
   ],
  providers: [RoleGuard, httpInterceptorProviders, BusService,SubwayService,TrolleyService,ControllerService, AdministratorService,LineService,StationService, 
    LocationService, TimetableService, PricelistService, DocumentService, TicketService, PassengerService, HttpClientModule,
    { provide: MatDialogRef, useValue: {} },
    { provide: MAT_DIALOG_DATA, useValue: [] },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
