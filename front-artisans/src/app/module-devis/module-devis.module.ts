import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ModuleDevisRoutingModule} from './module-devis-routing.module';
import {DevisComponent} from './devis/devis.component';


@NgModule({
  declarations: [DevisComponent],
  imports: [
    CommonModule,
    ModuleDevisRoutingModule
  ]
})
export class ModuleDevisModule { }
