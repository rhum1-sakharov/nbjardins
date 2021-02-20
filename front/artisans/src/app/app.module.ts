import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {ModuleParametresModule} from './module-parametres/module-parametres.module';
import {ModuleDevisModule} from './module-devis/module-devis.module';
import {CoreLibModule} from 'rhum1-sakharov-core-lib';
import {MessageService} from 'primeng/api';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthInterceptorService} from './login/auth-interceptor.service';
import {ToastModule} from 'primeng/toast';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ModuleCoreModule} from './module-core/module-core.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ModuleParametresModule,
    ModuleDevisModule,
    CoreLibModule,
    ToastModule,
    ModuleCoreModule,
    HttpClientModule

  ],
  providers: [
    MessageService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
