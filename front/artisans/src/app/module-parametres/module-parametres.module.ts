import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ModuleParametresRoutingModule} from './module-parametres-routing.module';
import {ParametresComponent} from './parametres/parametres.component';
import {CoreLibModule} from 'rhum1-sakharov-core-lib';


@NgModule({
  declarations: [ParametresComponent],
  imports: [
    CommonModule,
    ModuleParametresRoutingModule,
    CoreLibModule
  ]
})
export class ModuleParametresModule { }
