import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'aeroclub',
        data: { pageTitle: 'aeroclubpassionApp.aeroclub.home.title' },
        loadChildren: () => import('./aeroclub/aeroclub.module').then(m => m.AeroclubModule),
      },
      {
        path: 'user-registered',
        data: { pageTitle: 'aeroclubpassionApp.userRegistered.home.title' },
        loadChildren: () => import('./user-registered/user-registered.module').then(m => m.UserRegisteredModule),
      },
      {
        path: 'tarif',
        data: { pageTitle: 'aeroclubpassionApp.tarif.home.title' },
        loadChildren: () => import('./tarif/tarif.module').then(m => m.TarifModule),
      },
      {
        path: 'avion',
        data: { pageTitle: 'aeroclubpassionApp.avion.home.title' },
        loadChildren: () => import('./avion/avion.module').then(m => m.AvionModule),
      },
      {
        path: 'reservation',
        data: { pageTitle: 'aeroclubpassionApp.reservation.home.title' },
        loadChildren: () => import('./reservation/reservation.module').then(m => m.ReservationModule),
      },
      {
        path: 'revision',
        data: { pageTitle: 'aeroclubpassionApp.revision.home.title' },
        loadChildren: () => import('./revision/revision.module').then(m => m.RevisionModule),
      },
      {
        path: 'atelier',
        data: { pageTitle: 'aeroclubpassionApp.atelier.home.title' },
        loadChildren: () => import('./atelier/atelier.module').then(m => m.AtelierModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
