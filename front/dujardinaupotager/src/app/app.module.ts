import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ArrosoirComponent} from './components/svg/arrosoir/arrosoir.component';
import {LeavesBagComponent} from './components/svg/leaves-bag/leaves-bag.component';
import {TonteComponent} from './components/svg/tonte/tonte.component';
import {DesherbageComponent} from './components/svg/desherbage/desherbage.component';
import {TailleComponent} from './components/svg/taille/taille.component';
import {PlantationComponent} from './components/svg/plantation/plantation.component';
import {PaillageComponent} from './components/svg/paillage/paillage.component';
import {EntretienPiscineComponent} from './components/svg/entretien-piscine/entretien-piscine.component';
import {TopMenuComponent} from './components/top-menu/top-menu.component';
import {DujardinaupotagerComponent} from './components/content/dujardinaupotager/dujardinaupotager.component';
import {ServicesProposesComponent} from './components/content/services-proposes/services-proposes.component';
import {ContentComponent} from './components/content/content.component';
import {FooterComponent} from './components/footer/footer.component';
import {AutresServicesComponent} from './components/content/autres-services/autres-services.component';
import {MeContacterComponent} from './components/content/me-contacter/me-contacter.component';
import {ContactMeComponent} from './components/svg/contact-me/contact-me.component';
import {TooltipModule} from 'primeng/tooltip';
import {CesuComponent} from './components/svg/cesu/cesu.component';
import {CardModule} from 'primeng/card';

@NgModule({
  declarations: [
    AppComponent,
    ArrosoirComponent,
    LeavesBagComponent,
    TonteComponent,
    DesherbageComponent,
    TailleComponent,
    PlantationComponent,
    PaillageComponent,
    EntretienPiscineComponent,
    TopMenuComponent,
    DujardinaupotagerComponent,
    ServicesProposesComponent,
    ContentComponent,
    FooterComponent,
    MeContacterComponent,
    AutresServicesComponent,
    ContactMeComponent,
    CesuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TooltipModule,
    CardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
