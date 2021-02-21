import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParametresComponent} from './parametres/parametres.component';
import {ParametresResolverService} from './parametres/parametres-resolver.service';
import {AuthGuard, USER_RIGHT_ARTISAN} from 'rhum1-sakharov-core-lib';

const routes: Routes = [
  {
    path: 'parametres',
    component: ParametresComponent,
    canActivate: [AuthGuard],
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
    resolve: {
      parametresSupplier: ParametresResolverService
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModuleParametresRoutingModule {
}
