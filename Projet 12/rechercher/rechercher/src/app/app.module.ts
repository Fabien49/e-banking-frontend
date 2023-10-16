import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// import ReactiveFormsModule for reactive form
import { ReactiveFormsModule } from '@angular/forms';

// import Http module
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AeroclubComponent } from './aeroclub/aeroclub.component';
import { UserService } from './services/user.service';

@NgModule({
  declarations: [
    AppComponent,
    AeroclubComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
