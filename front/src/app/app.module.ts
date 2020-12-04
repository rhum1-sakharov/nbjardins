import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {CardModule} from "primeng/card";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {CoreModule} from "./core/core.module";
import {AutoCompleteModule} from "primeng/autocomplete";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AccueilComponent} from './accueil/accueil.component';
import {DemandeDeDevisComponent} from './demande-de-devis/demande-de-devis.component';
import {ToastModule} from "primeng/toast";
import {MessagesModule} from "primeng/messages";
import {MessageModule} from "primeng/message";
import {MessageService} from "primeng/api";

@NgModule({
  declarations: [
    AppComponent,
    AccueilComponent,
    DemandeDeDevisComponent
  ],
  imports: [
    CoreModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    AutoCompleteModule,
    FormsModule,
    FontAwesomeModule,
    CardModule,
    InputTextModule,
    InputTextareaModule,
    ButtonModule,
    ToastModule,
    MessageModule,
    MessagesModule

  ],
  providers: [
    MessageService,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
