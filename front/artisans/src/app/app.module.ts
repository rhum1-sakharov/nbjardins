import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {ConfirmationService, MessageService} from 'primeng/api';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthInterceptorService} from '../../../core-lib/projects/core-lib/src/lib/techniques/auth/auth-interceptor.service';
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
import {PreviewDevisComponent} from './components/preview-devis/preview-devis.component';
import {PreviewFactureComponent} from './components/preview-facture/preview-facture.component';
import {SelectButtonModule} from 'primeng/selectbutton';
import {StatsComponent} from './pages/stats/stats.component';
import {CheckboxModule} from 'primeng/checkbox';
import {TableModule} from 'primeng/table';
import {RadioButtonModule} from 'primeng/radiobutton';
import {CrudBanqueComponent} from './components/cruds/banque/crud-banque.component';
import {DialogModule} from 'primeng/dialog';
import {InputMaskModule} from 'primeng/inputmask';
import {TabViewModule} from 'primeng/tabview';
import {BadgeModule} from 'primeng/badge';
import {TabMenuModule} from 'primeng/tabmenu';
import {ATraiterComponent} from './components/devis/states/a-traiter/a-traiter.component';
import {TraitesComponent} from './components/devis/states/traites/traites.component';
import {AcceptesComponent} from './components/devis/states/acceptes/acceptes.component';
import {RefusesComponent} from './components/devis/states/refuses/refuses.component';
import {AbandonnesComponent} from './components/devis/states/abandonnes/abandonnes.component';
import {CreateComponent} from './components/devis/states/a-traiter/dialogs/create/create.component';
import {DropdownModule} from 'primeng/dropdown';
import {DesignerComponent} from './components/devis/designer/designer.component';
import {EditTextFieldComponent} from './components/devis/designer/edit-text-field/edit-text-field.component';
import {EditDateFieldComponent} from './components/devis/designer/edit-date-field/edit-date-field.component';
import {CalendarModule} from 'primeng/calendar';
import {EditEmailFieldComponent} from './components/devis/designer/edit-email-field/edit-email-field.component';

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
    PreviewFactureComponent,
    StatsComponent,
    CrudBanqueComponent,
    ATraiterComponent,
    TraitesComponent,
    AcceptesComponent,
    RefusesComponent,
    AbandonnesComponent,
    CreateComponent,
    DesignerComponent,
    EditTextFieldComponent,
    EditDateFieldComponent,
    EditEmailFieldComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
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
    SelectButtonModule,
    CheckboxModule,
    TableModule,
    RadioButtonModule,
    DialogModule,
    InputMaskModule,
    TabViewModule,
    BadgeModule,
    TabMenuModule,
    DropdownModule,
    CalendarModule

  ],
  providers: [
    MessageService,
    ConfirmationService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
  ],
  bootstrap:
    [AppComponent]
})

export class AppModule {
}
