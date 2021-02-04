import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DevisComponent} from './devis/devis.component';

const routes: Routes = [
  {
    path: 'devis',
    component: DevisComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModuleDevisRoutingModule { }
