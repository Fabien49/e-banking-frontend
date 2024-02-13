jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AeroclubService } from '../service/aeroclub.service';
import { IAeroclub, Aeroclub } from '../aeroclub.model';
import { ITarif } from 'app/entities/tarif/tarif.model';
import { TarifService } from 'app/entities/tarif/service/tarif.service';

import { AeroclubUpdateComponent } from './aeroclub-update.component';

describe('Aeroclub Management Update Component', () => {
  let comp: AeroclubUpdateComponent;
  let fixture: ComponentFixture<AeroclubUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let aeroclubService: AeroclubService;
  let tarifService: TarifService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AeroclubUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AeroclubUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AeroclubUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    aeroclubService = TestBed.inject(AeroclubService);
    tarifService = TestBed.inject(TarifService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call tarif query and add missing value', () => {
      const aeroclub: IAeroclub = { id: 456 };
      const tarif: ITarif = { id: 56555 };
      aeroclub.tarif = tarif;

      const tarifCollection: ITarif[] = [{ id: 98036 }];
      jest.spyOn(tarifService, 'query').mockReturnValue(of(new HttpResponse({ body: tarifCollection })));
      const expectedCollection: ITarif[] = [tarif, ...tarifCollection];
      jest.spyOn(tarifService, 'addTarifToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ aeroclub });
      comp.ngOnInit();

      expect(tarifService.query).toHaveBeenCalled();
      expect(tarifService.addTarifToCollectionIfMissing).toHaveBeenCalledWith(tarifCollection, tarif);
      expect(comp.tarifsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const aeroclub: IAeroclub = { id: 456 };
      const tarif: ITarif = { id: 19542 };
      aeroclub.tarif = tarif;

      activatedRoute.data = of({ aeroclub });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(aeroclub));
      expect(comp.tarifsCollection).toContain(tarif);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aeroclub>>();
      const aeroclub = { id: 123 };
      jest.spyOn(aeroclubService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aeroclub });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aeroclub }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(aeroclubService.update).toHaveBeenCalledWith(aeroclub);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aeroclub>>();
      const aeroclub = new Aeroclub();
      jest.spyOn(aeroclubService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aeroclub });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aeroclub }));
      saveSubject.complete();

      // THEN
      expect(aeroclubService.create).toHaveBeenCalledWith(aeroclub);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aeroclub>>();
      const aeroclub = { id: 123 };
      jest.spyOn(aeroclubService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aeroclub });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(aeroclubService.update).toHaveBeenCalledWith(aeroclub);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTarifById', () => {
      it('Should return tracked Tarif primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTarifById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
