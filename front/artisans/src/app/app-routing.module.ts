import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParametresResolverService} from './pages/parametres/parametres-resolver.service';
import {ParametresComponent} from './pages/parametres/parametres.component';
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
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
