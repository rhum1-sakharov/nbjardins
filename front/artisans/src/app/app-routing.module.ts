import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParametresResolverService} from './pages/parametres/parametres-resolver.service';
import {ParametresComponent} from './pages/parametres/parametres.component';
import {AuthGuard, USER_RIGHT_ARTISAN} from 'rhum1-sakharov-core-lib';
import {DevisComponent} from './pages/devis/devis.component';
import {FacturesComponent} from './pages/factures/factures.component';
import {StatsComponent} from './pages/stats/stats.component';


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
  },
  {
    path: 'devis',
    component: DevisComponent,
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
  },
  {
    path: 'factures',
    component: FacturesComponent,
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
  },
  {
    path: 'stats',
    component: StatsComponent,
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
