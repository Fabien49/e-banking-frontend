import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserRegistered, getUserRegisteredIdentifier } from '../user-registered.model';

export type EntityResponseType = HttpResponse<IUserRegistered>;
export type EntityArrayResponseType = HttpResponse<IUserRegistered[]>;

@Injectable({ providedIn: 'root' })
export class UserRegisteredService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-registereds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userRegistered: IUserRegistered): Observable<EntityResponseType> {
    return this.http.post<IUserRegistered>(this.resourceUrl, userRegistered, { observe: 'response' });
  }

  update(userRegistered: IUserRegistered): Observable<EntityResponseType> {
    return this.http.put<IUserRegistered>(`${this.resourceUrl}/${getUserRegisteredIdentifier(userRegistered) as number}`, userRegistered, {
      observe: 'response',
    });
  }

  partialUpdate(userRegistered: IUserRegistered): Observable<EntityResponseType> {
    return this.http.patch<IUserRegistered>(
      `${this.resourceUrl}/${getUserRegisteredIdentifier(userRegistered) as number}`,
      userRegistered,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserRegistered>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserRegistered[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserRegisteredToCollectionIfMissing(
    userRegisteredCollection: IUserRegistered[],
    ...userRegisteredsToCheck: (IUserRegistered | null | undefined)[]
  ): IUserRegistered[] {
    const userRegistereds: IUserRegistered[] = userRegisteredsToCheck.filter(isPresent);
    if (userRegistereds.length > 0) {
      const userRegisteredCollectionIdentifiers = userRegisteredCollection.map(
        userRegisteredItem => getUserRegisteredIdentifier(userRegisteredItem)!
      );
      const userRegisteredsToAdd = userRegistereds.filter(userRegisteredItem => {
        const userRegisteredIdentifier = getUserRegisteredIdentifier(userRegisteredItem);
        if (userRegisteredIdentifier == null || userRegisteredCollectionIdentifiers.includes(userRegisteredIdentifier)) {
          return false;
        }
        userRegisteredCollectionIdentifiers.push(userRegisteredIdentifier);
        return true;
      });
      return [...userRegisteredsToAdd, ...userRegisteredCollection];
    }
    return userRegisteredCollection;
  }
}
