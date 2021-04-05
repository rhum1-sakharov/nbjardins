import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParametresResolverService} from './pages/parametres/parametres-resolver.service';
import {ParametresComponent} from './pages/parametres/parametres.component';
import {AuthGuard, USER_RIGHT_ARTISAN} from 'rhum1-sakharov-core-lib';
import {DevisComponent} from './pages/devis/devis.component';
import {FacturesComponent} from './pages/factures/factures.component';
import {StatsComponent} from './pages/stats/stats.component';
import {DevisResolverService} from './pages/devis/devis-resolver.service';
import {ATraiterComponent} from './components/devis/a-traiter/a-traiter.component';
import {TraitesComponent} from './components/devis/traites/traites.component';
import {AcceptesComponent} from './components/devis/acceptes/acceptes.component';
import {RefusesComponent} from './components/devis/refuses/refuses.component';
import {AbandonnesComponent} from './components/devis/abandonnes/abandonnes.component';


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
    canActivate: [AuthGuard],
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
    resolve: {
      devisSupplier: DevisResolverService
    },
    children: [
      {
        path: 'a-traiter',
        component: ATraiterComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
      },
      {
        path: 'traites',
        component: TraitesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
      },
      {
        path: 'acceptes',
        component: AcceptesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
      },
      {
        path: 'refuses',
        component: RefusesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
      },
      {
        path: 'abandonnes',
        component: AbandonnesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
      },
    ]
  },
  {
    path: 'factures',
    canActivate: [AuthGuard],
    component: FacturesComponent,
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
  },
  {
    path: 'stats',
    component: StatsComponent,
    canActivate: [AuthGuard],
    runGuardsAndResolvers: 'always',
    data: USER_RIGHT_ARTISAN,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
