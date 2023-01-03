import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './common/routing/app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { FileManagerComponent } from './file-manager/file-manager.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {CommonModule} from "@angular/common";
import {MatTableModule} from "@angular/material/table";
import {FormsModule} from "@angular/forms";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatIconModule} from "@angular/material/icon";
import { SpinnerComponent } from './common/spinner/spinner.component';
import {LoadingInterceptor} from "./common/spinner/loading.interceptor";
import {MatTooltipModule} from "@angular/material/tooltip";
import { TerminalComponent } from './terminal/terminal.component';
import { HistoryComponent } from './history/history.component';
import {MatRippleModule} from "@angular/material/core";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatChipsModule} from "@angular/material/chips";
import { ZipComponent } from './zip/zip.component';

@NgModule({
  declarations: [
    AppComponent,
    FileManagerComponent,
    SpinnerComponent,
    TerminalComponent,
    HistoryComponent,
    ZipComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatToolbarModule,
    MatSidenavModule,
    MatTableModule,
    FormsModule,
    MatCheckboxModule,
    MatIconModule,
    MatTooltipModule,
    MatRippleModule,
    MatButtonToggleModule,
    MatChipsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
