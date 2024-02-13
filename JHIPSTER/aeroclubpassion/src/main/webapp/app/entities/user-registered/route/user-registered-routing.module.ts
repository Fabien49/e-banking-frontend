import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserRegisteredComponent } from '../list/user-registered.component';
import { UserRegisteredDetailComponent } from '../detail/user-registered-detail.component';
import { UserRegisteredUpdateComponent } from '../update/user-registered-update.component';
import { UserRegisteredRoutingResolveService } from './user-registered-routing-resolve.service';

const userRegisteredRoute: Routes = [
  {
    path: '',
    component: UserRegisteredComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserRegisteredDetailComponent,
    resolve: {
      userRegistered: UserRegisteredRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserRegisteredUpdateComponent,
    resolve: {
      userRegistered: UserRegisteredRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserRegisteredUpdateComponent,
    resolve: {
      userRegistered: UserRegisteredRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userRegisteredRoute)],
  exports: [RouterModule],
})
export class UserRegisteredRoutingModule {}
