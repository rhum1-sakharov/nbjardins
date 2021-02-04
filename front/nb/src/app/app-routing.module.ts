import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AccueilComponent} from "./accueil/accueil.component";
import {DemandeDeDevisComponent} from "./demande-de-devis/demande-de-devis.component";

const routes: Routes = [{
  path: 'accueil',
  component: AccueilComponent
}, {
  path: 'demander-un-devis',
  component: DemandeDeDevisComponent
},
  {
    path: '',
    redirectTo: 'accueil',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {relativeLinkResolution: 'legacy'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
