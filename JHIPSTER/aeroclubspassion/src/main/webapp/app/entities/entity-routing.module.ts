import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'aeroclub',
        data: { pageTitle: 'aeroclubspassionApp.aeroclub.home.title' },
        loadChildren: () => import('./aeroclub/aeroclub.module').then(m => m.AeroclubModule),
      },
      {
        path: 'tarif',
        data: { pageTitle: 'aeroclubspassionApp.tarif.home.title' },
        loadChildren: () => import('./tarif/tarif.module').then(m => m.TarifModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
