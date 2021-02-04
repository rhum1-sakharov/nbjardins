import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ModuleParametresRoutingModule} from './module-parametres-routing.module';
import {ParametresComponent} from './parametres/parametres.component';


@NgModule({
  declarations: [ParametresComponent],
  imports: [
    CommonModule,
    ModuleParametresRoutingModule
  ]
})
export class ModuleParametresModule { }
