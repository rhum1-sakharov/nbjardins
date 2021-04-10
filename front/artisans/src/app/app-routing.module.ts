import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParametresResolverService} from './pages/parametres/parametres-resolver.service';
import {ParametresComponent} from './pages/parametres/parametres.component';
import {AuthGuard, USER_RIGHT_ARTISAN} from 'rhum1-sakharov-core-lib';
import {DevisComponent} from './pages/devis/devis.component';
import {FacturesComponent} from './pages/factures/factures.component';
import {StatsComponent} from './pages/stats/stats.component';
import {DevisResolverService} from './pages/devis/devis-resolver.service';
import {ATraiterComponent} from './components/devis/states/a-traiter/a-traiter.component';
import {TraitesComponent} from './components/devis/states/traites/traites.component';
import {AcceptesComponent} from './components/devis/states/acceptes/acceptes.component';
import {RefusesComponent} from './components/devis/states/refuses/refuses.component';
import {AbandonnesComponent} from './components/devis/states/abandonnes/abandonnes.component';
import {ATraiterResolverService} from './components/devis/states/a-traiter/a-traiter-resolver.service';
import {TraitesResolverService} from './components/devis/states/traites/traites-resolver.service';
import {AcceptesResolverService} from './components/devis/states/acceptes/acceptes-resolver.service';
import {RefusesResolverService} from './components/devis/states/refuses/refuses-resolver.service';
import {AbandonnesResolverService} from './components/devis/states/abandonnes/abandonnes-resolver.service';


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
        resolve: {
          devisATraiterSupplier: ATraiterResolverService
        },
      },
      {
        path: 'traites',
        component: TraitesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
        resolve: {
          devisTraitesSupplier: TraitesResolverService
        },
      },
      {
        path: 'acceptes',
        component: AcceptesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
        resolve: {
          devisAcceptesSupplier: AcceptesResolverService
        },
      },
      {
        path: 'refuses',
        component: RefusesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
        resolve: {
          devisRefusesSupplier: RefusesResolverService
        },
      },
      {
        path: 'abandonnes',
        component: AbandonnesComponent,
        canActivate: [AuthGuard],
        runGuardsAndResolvers: 'always',
        data: USER_RIGHT_ARTISAN,
        resolve: {
          devisAbandonnesSupplier: AbandonnesResolverService
        },
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
