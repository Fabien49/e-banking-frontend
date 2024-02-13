jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UserRegisteredService } from '../service/user-registered.service';
import { IUserRegistered, UserRegistered } from '../user-registered.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { UserRegisteredUpdateComponent } from './user-registered-update.component';

describe('UserRegistered Management Update Component', () => {
  let comp: UserRegisteredUpdateComponent;
  let fixture: ComponentFixture<UserRegisteredUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userRegisteredService: UserRegisteredService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserRegisteredUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(UserRegisteredUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserRegisteredUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userRegisteredService = TestBed.inject(UserRegisteredService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const userRegistered: IUserRegistered = { id: 456 };
      const user: IUser = { id: 22598 };
      userRegistered.user = user;

      const userCollection: IUser[] = [{ id: 66828 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userRegistered });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userRegistered: IUserRegistered = { id: 456 };
      const user: IUser = { id: 63880 };
      userRegistered.user = user;

      activatedRoute.data = of({ userRegistered });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userRegistered));
      expect(comp.usersSharedCollection).toContain(user);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserRegistered>>();
      const userRegistered = { id: 123 };
      jest.spyOn(userRegisteredService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userRegistered });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userRegistered }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userRegisteredService.update).toHaveBeenCalledWith(userRegistered);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserRegistered>>();
      const userRegistered = new UserRegistered();
      jest.spyOn(userRegisteredService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userRegistered });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userRegistered }));
      saveSubject.complete();

      // THEN
      expect(userRegisteredService.create).toHaveBeenCalledWith(userRegistered);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserRegistered>>();
      const userRegistered = { id: 123 };
      jest.spyOn(userRegisteredService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userRegistered });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userRegisteredService.update).toHaveBeenCalledWith(userRegistered);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
