import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './helpers/routing/app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from "ngx-toastr";
import {AuthInterceptor} from "./helpers/auth/auth.interceptor";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './helpers/auth/login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { RegistrationComponent } from './helpers/auth/registration/registration.component';
import { JsonSchemaComponent } from './json-schema/json-schema.component';
import {HIGHLIGHT_OPTIONS, HighlightModule} from "ngx-highlightjs";
import {MatInputModule} from "@angular/material/input";
import {ClipboardModule} from "@angular/cdk/clipboard";
import {MatRadioModule} from "@angular/material/radio";
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    JsonSchemaComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    MatIconModule,
    HighlightModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    ReactiveFormsModule,
    MatToolbarModule,
    MatButtonToggleModule,
    FormsModule,
    MatInputModule,
    ClipboardModule,
    MatRadioModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    {
      provide: HIGHLIGHT_OPTIONS,
      useValue: {
        fullLibraryLoader: () => import('highlight.js'),
      }
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {



}
