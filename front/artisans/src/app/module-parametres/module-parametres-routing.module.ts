import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParametresComponent} from './parametres/parametres.component';
import {AuthGuard} from '../login/auth.guard';
import {USER_RIGHT_ARTISAN} from '../login/auth.service';
import {ParametresResolverService} from './parametres/parametres-resolver.service';

const routes: Routes = [
  {
    path: 'parametres',
    component: ParametresComponent,
    canActivate: [AuthGuard],
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
    resolve:{
      parametresSupplier : ParametresResolverService
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModuleParametresRoutingModule { }
