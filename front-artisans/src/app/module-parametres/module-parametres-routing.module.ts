import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParametresComponent} from './parametres/parametres.component';
import {AuthGuard} from '../login/auth.guard';
import {USER_RIGHT_ARTISAN} from '../login/auth.service';

const routes: Routes = [
  {
    path: 'parametres',
    component: ParametresComponent,
    canActivate: [AuthGuard],
    data: USER_RIGHT_ARTISAN
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ModuleParametresRoutingModule { }
