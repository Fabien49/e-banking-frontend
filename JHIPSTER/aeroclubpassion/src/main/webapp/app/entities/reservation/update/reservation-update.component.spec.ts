jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ReservationService } from '../service/reservation.service';
import { IReservation, Reservation } from '../reservation.model';
import { IAvion } from 'app/entities/avion/avion.model';
import { AvionService } from 'app/entities/avion/service/avion.service';
import { IUserRegistered } from 'app/entities/user-registered/user-registered.model';
import { UserRegisteredService } from 'app/entities/user-registered/service/user-registered.service';

import { ReservationUpdateComponent } from './reservation-update.component';

describe('Reservation Management Update Component', () => {
  let comp: ReservationUpdateComponent;
  let fixture: ComponentFixture<ReservationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reservationService: ReservationService;
  let avionService: AvionService;
  let userRegisteredService: UserRegisteredService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReservationUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ReservationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReservationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reservationService = TestBed.inject(ReservationService);
    avionService = TestBed.inject(AvionService);
    userRegisteredService = TestBed.inject(UserRegisteredService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Avion query and add missing value', () => {
      const reservation: IReservation = { id: 456 };
      const avions: IAvion = { id: 37402 };
      reservation.avions = avions;

      const avionCollection: IAvion[] = [{ id: 68905 }];
      jest.spyOn(avionService, 'query').mockReturnValue(of(new HttpResponse({ body: avionCollection })));
      const additionalAvions = [avions];
      const expectedCollection: IAvion[] = [...additionalAvions, ...avionCollection];
      jest.spyOn(avionService, 'addAvionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      expect(avionService.query).toHaveBeenCalled();
      expect(avionService.addAvionToCollectionIfMissing).toHaveBeenCalledWith(avionCollection, ...additionalAvions);
      expect(comp.avionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UserRegistered query and add missing value', () => {
      const reservation: IReservation = { id: 456 };
      const userRegistereds: IUserRegistered = { id: 94228 };
      reservation.userRegistereds = userRegistereds;

      const userRegisteredCollection: IUserRegistered[] = [{ id: 73587 }];
      jest.spyOn(userRegisteredService, 'query').mockReturnValue(of(new HttpResponse({ body: userRegisteredCollection })));
      const additionalUserRegistereds = [userRegistereds];
      const expectedCollection: IUserRegistered[] = [...additionalUserRegistereds, ...userRegisteredCollection];
      jest.spyOn(userRegisteredService, 'addUserRegisteredToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      expect(userRegisteredService.query).toHaveBeenCalled();
      expect(userRegisteredService.addUserRegisteredToCollectionIfMissing).toHaveBeenCalledWith(
        userRegisteredCollection,
        ...additionalUserRegistereds
      );
      expect(comp.userRegisteredsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const reservation: IReservation = { id: 456 };
      const avions: IAvion = { id: 73626 };
      reservation.avions = avions;
      const userRegistereds: IUserRegistered = { id: 92810 };
      reservation.userRegistereds = userRegistereds;

      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(reservation));
      expect(comp.avionsSharedCollection).toContain(avions);
      expect(comp.userRegisteredsSharedCollection).toContain(userRegistereds);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reservation>>();
      const reservation = { id: 123 };
      jest.spyOn(reservationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reservation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(reservationService.update).toHaveBeenCalledWith(reservation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reservation>>();
      const reservation = new Reservation();
      jest.spyOn(reservationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reservation }));
      saveSubject.complete();

      // THEN
      expect(reservationService.create).toHaveBeenCalledWith(reservation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Reservation>>();
      const reservation = { id: 123 };
      jest.spyOn(reservationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reservationService.update).toHaveBeenCalledWith(reservation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAvionById', () => {
      it('Should return tracked Avion primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAvionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserRegisteredById', () => {
      it('Should return tracked UserRegistered primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserRegisteredById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
