import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAtelier, Atelier } from '../atelier.model';

import { AtelierService } from './atelier.service';

describe('Atelier Service', () => {
  let service: AtelierService;
  let httpMock: HttpTestingController;
  let elemDefault: IAtelier;
  let expectedResult: IAtelier | IAtelier[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AtelierService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      compteurChgtMoteur: 0,
      compteurCarrosserie: 0,
      compteurHelisse: 0,
      date: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Atelier', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.create(new Atelier()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Atelier', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          compteurChgtMoteur: 1,
          compteurCarrosserie: 1,
          compteurHelisse: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Atelier', () => {
      const patchObject = Object.assign(
        {
          compteurChgtMoteur: 1,
        },
        new Atelier()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Atelier', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          compteurChgtMoteur: 1,
          compteurCarrosserie: 1,
          compteurHelisse: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Atelier', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAtelierToCollectionIfMissing', () => {
      it('should add a Atelier to an empty array', () => {
        const atelier: IAtelier = { id: 123 };
        expectedResult = service.addAtelierToCollectionIfMissing([], atelier);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atelier);
      });

      it('should not add a Atelier to an array that contains it', () => {
        const atelier: IAtelier = { id: 123 };
        const atelierCollection: IAtelier[] = [
          {
            ...atelier,
          },
          { id: 456 },
        ];
        expectedResult = service.addAtelierToCollectionIfMissing(atelierCollection, atelier);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Atelier to an array that doesn't contain it", () => {
        const atelier: IAtelier = { id: 123 };
        const atelierCollection: IAtelier[] = [{ id: 456 }];
        expectedResult = service.addAtelierToCollectionIfMissing(atelierCollection, atelier);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atelier);
      });

      it('should add only unique Atelier to an array', () => {
        const atelierArray: IAtelier[] = [{ id: 123 }, { id: 456 }, { id: 24744 }];
        const atelierCollection: IAtelier[] = [{ id: 123 }];
        expectedResult = service.addAtelierToCollectionIfMissing(atelierCollection, ...atelierArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const atelier: IAtelier = { id: 123 };
        const atelier2: IAtelier = { id: 456 };
        expectedResult = service.addAtelierToCollectionIfMissing([], atelier, atelier2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atelier);
        expect(expectedResult).toContain(atelier2);
      });

      it('should accept null and undefined values', () => {
        const atelier: IAtelier = { id: 123 };
        expectedResult = service.addAtelierToCollectionIfMissing([], null, atelier, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atelier);
      });

      it('should return initial array if no Atelier is added', () => {
        const atelierCollection: IAtelier[] = [{ id: 123 }];
        expectedResult = service.addAtelierToCollectionIfMissing(atelierCollection, undefined, null);
        expect(expectedResult).toEqual(atelierCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
