import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserRegistered, UserRegistered } from '../user-registered.model';
import { UserRegisteredService } from '../service/user-registered.service';

@Injectable({ providedIn: 'root' })
export class UserRegisteredRoutingResolveService implements Resolve<IUserRegistered> {
  constructor(protected service: UserRegisteredService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserRegistered> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userRegistered: HttpResponse<UserRegistered>) => {
          if (userRegistered.body) {
            return of(userRegistered.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserRegistered());
  }
}
