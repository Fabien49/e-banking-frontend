jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IUserRegistered, UserRegistered } from '../user-registered.model';
import { UserRegisteredService } from '../service/user-registered.service';

import { UserRegisteredRoutingResolveService } from './user-registered-routing-resolve.service';

describe('UserRegistered routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UserRegisteredRoutingResolveService;
  let service: UserRegisteredService;
  let resultUserRegistered: IUserRegistered | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(UserRegisteredRoutingResolveService);
    service = TestBed.inject(UserRegisteredService);
    resultUserRegistered = undefined;
  });

  describe('resolve', () => {
    it('should return IUserRegistered returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserRegistered = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserRegistered).toEqual({ id: 123 });
    });

    it('should return new IUserRegistered if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserRegistered = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUserRegistered).toEqual(new UserRegistered());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UserRegistered })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserRegistered = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserRegistered).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
