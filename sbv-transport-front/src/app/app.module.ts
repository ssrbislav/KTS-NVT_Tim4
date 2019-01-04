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

const appRoutes: Routes = [
  { path: 'registration', component: RegistrationComponent },
  { path: 'mainPage', component: MainPageComponent },
  { path: '',
    redirectTo: '/mainPage',
    pathMatch: 'full'
  },
  { path: 'administrator', component: AdministratorComponent},
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
    BusTableComponent
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
  providers: [BusService],
  bootstrap: [AppComponent]
})
export class AppModule { }
