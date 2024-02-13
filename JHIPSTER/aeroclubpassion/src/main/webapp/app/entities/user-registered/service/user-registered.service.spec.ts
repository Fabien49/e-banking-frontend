import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserRegistered, UserRegistered } from '../user-registered.model';

import { UserRegisteredService } from './user-registered.service';

describe('UserRegistered Service', () => {
  let service: UserRegisteredService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserRegistered;
  let expectedResult: IUserRegistered | IUserRegistered[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserRegisteredService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nom: 'AAAAAAA',
      prenom: 'AAAAAAA',
      telephone: 'AAAAAAA',
      mail: 'AAAAAAA',
      adresse: 'AAAAAAA',
      codePostal: 'AAAAAAA',
      commune: 'AAAAAAA',
      heureVol: 'PT1S',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a UserRegistered', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new UserRegistered()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserRegistered', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          telephone: 'BBBBBB',
          mail: 'BBBBBB',
          adresse: 'BBBBBB',
          codePostal: 'BBBBBB',
          commune: 'BBBBBB',
          heureVol: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserRegistered', () => {
      const patchObject = Object.assign(
        {
          prenom: 'BBBBBB',
          telephone: 'BBBBBB',
          mail: 'BBBBBB',
          codePostal: 'BBBBBB',
        },
        new UserRegistered()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserRegistered', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          telephone: 'BBBBBB',
          mail: 'BBBBBB',
          adresse: 'BBBBBB',
          codePostal: 'BBBBBB',
          commune: 'BBBBBB',
          heureVol: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a UserRegistered', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserRegisteredToCollectionIfMissing', () => {
      it('should add a UserRegistered to an empty array', () => {
        const userRegistered: IUserRegistered = { id: 123 };
        expectedResult = service.addUserRegisteredToCollectionIfMissing([], userRegistered);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userRegistered);
      });

      it('should not add a UserRegistered to an array that contains it', () => {
        const userRegistered: IUserRegistered = { id: 123 };
        const userRegisteredCollection: IUserRegistered[] = [
          {
            ...userRegistered,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserRegisteredToCollectionIfMissing(userRegisteredCollection, userRegistered);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserRegistered to an array that doesn't contain it", () => {
        const userRegistered: IUserRegistered = { id: 123 };
        const userRegisteredCollection: IUserRegistered[] = [{ id: 456 }];
        expectedResult = service.addUserRegisteredToCollectionIfMissing(userRegisteredCollection, userRegistered);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userRegistered);
      });

      it('should add only unique UserRegistered to an array', () => {
        const userRegisteredArray: IUserRegistered[] = [{ id: 123 }, { id: 456 }, { id: 74321 }];
        const userRegisteredCollection: IUserRegistered[] = [{ id: 123 }];
        expectedResult = service.addUserRegisteredToCollectionIfMissing(userRegisteredCollection, ...userRegisteredArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userRegistered: IUserRegistered = { id: 123 };
        const userRegistered2: IUserRegistered = { id: 456 };
        expectedResult = service.addUserRegisteredToCollectionIfMissing([], userRegistered, userRegistered2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userRegistered);
        expect(expectedResult).toContain(userRegistered2);
      });

      it('should accept null and undefined values', () => {
        const userRegistered: IUserRegistered = { id: 123 };
        expectedResult = service.addUserRegisteredToCollectionIfMissing([], null, userRegistered, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userRegistered);
      });

      it('should return initial array if no UserRegistered is added', () => {
        const userRegisteredCollection: IUserRegistered[] = [{ id: 123 }];
        expectedResult = service.addUserRegisteredToCollectionIfMissing(userRegisteredCollection, undefined, null);
        expect(expectedResult).toEqual(userRegisteredCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
