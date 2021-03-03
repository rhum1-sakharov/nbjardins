import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {CoreLibModule} from 'rhum1-sakharov-core-lib';
import {ConfirmationService, MessageService} from 'primeng/api';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthInterceptorService} from '../../../core-lib/projects/core-lib/src/lib/techniques/auth-interceptor.service';
import {ToastModule} from 'primeng/toast';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ParametresComponent} from './pages/parametres/parametres.component';
import {TopMenuComponent} from './components/top-menu/top-menu.component';
import {TooltipModule} from 'primeng/tooltip';
import {ListboxModule} from 'primeng/listbox';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InputNumberModule} from 'primeng/inputnumber';
import {InputTextModule} from 'primeng/inputtext';
import {DevisComponent} from './pages/devis/devis.component';
import {FacturesComponent} from './pages/factures/factures.component';
import {AccordionModule} from 'primeng/accordion';
import {LoadingComponent} from './components/loading/loading.component';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import {ToolbarModule} from 'primeng/toolbar';
import {PreviewDevisComponent} from './pages/parametres/preview-devis/preview-devis.component';
import {PreviewFactureComponent} from './pages/parametres/preview-facture/preview-facture.component';
import {SelectButtonModule} from 'primeng/selectbutton';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ParametresComponent,
    TopMenuComponent,
    DevisComponent,
    FacturesComponent,
    LoadingComponent,
    PreviewDevisComponent,
    PreviewFactureComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    CoreLibModule,
    ToastModule,
    HttpClientModule,
    ConfirmDialogModule,
    TooltipModule,
    ListboxModule,
    InputNumberModule,
    InputTextModule,
    AccordionModule,
    ProgressSpinnerModule,
    ToolbarModule,
    SelectButtonModule

  ],
  providers: [
    MessageService,
    ConfirmationService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
