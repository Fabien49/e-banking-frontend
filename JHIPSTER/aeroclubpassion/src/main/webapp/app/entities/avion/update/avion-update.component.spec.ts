jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AvionService } from '../service/avion.service';
import { IAvion, Avion } from '../avion.model';
import { IAtelier } from 'app/entities/atelier/atelier.model';
import { AtelierService } from 'app/entities/atelier/service/atelier.service';
import { IRevision } from 'app/entities/revision/revision.model';
import { RevisionService } from 'app/entities/revision/service/revision.service';

import { AvionUpdateComponent } from './avion-update.component';

describe('Avion Management Update Component', () => {
  let comp: AvionUpdateComponent;
  let fixture: ComponentFixture<AvionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avionService: AvionService;
  let atelierService: AtelierService;
  let revisionService: RevisionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AvionUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AvionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avionService = TestBed.inject(AvionService);
    atelierService = TestBed.inject(AtelierService);
    revisionService = TestBed.inject(RevisionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Atelier query and add missing value', () => {
      const avion: IAvion = { id: 456 };
      const atelier: IAtelier = { id: 89396 };
      avion.atelier = atelier;

      const atelierCollection: IAtelier[] = [{ id: 92996 }];
      jest.spyOn(atelierService, 'query').mockReturnValue(of(new HttpResponse({ body: atelierCollection })));
      const additionalAteliers = [atelier];
      const expectedCollection: IAtelier[] = [...additionalAteliers, ...atelierCollection];
      jest.spyOn(atelierService, 'addAtelierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avion });
      comp.ngOnInit();

      expect(atelierService.query).toHaveBeenCalled();
      expect(atelierService.addAtelierToCollectionIfMissing).toHaveBeenCalledWith(atelierCollection, ...additionalAteliers);
      expect(comp.ateliersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Revision query and add missing value', () => {
      const avion: IAvion = { id: 456 };
      const revision: IRevision = { id: 95391 };
      avion.revision = revision;

      const revisionCollection: IRevision[] = [{ id: 84251 }];
      jest.spyOn(revisionService, 'query').mockReturnValue(of(new HttpResponse({ body: revisionCollection })));
      const additionalRevisions = [revision];
      const expectedCollection: IRevision[] = [...additionalRevisions, ...revisionCollection];
      jest.spyOn(revisionService, 'addRevisionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avion });
      comp.ngOnInit();

      expect(revisionService.query).toHaveBeenCalled();
      expect(revisionService.addRevisionToCollectionIfMissing).toHaveBeenCalledWith(revisionCollection, ...additionalRevisions);
      expect(comp.revisionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const avion: IAvion = { id: 456 };
      const atelier: IAtelier = { id: 91314 };
      avion.atelier = atelier;
      const revision: IRevision = { id: 12192 };
      avion.revision = revision;

      activatedRoute.data = of({ avion });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(avion));
      expect(comp.ateliersSharedCollection).toContain(atelier);
      expect(comp.revisionsSharedCollection).toContain(revision);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Avion>>();
      const avion = { id: 123 };
      jest.spyOn(avionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avion }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(avionService.update).toHaveBeenCalledWith(avion);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Avion>>();
      const avion = new Avion();
      jest.spyOn(avionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avion }));
      saveSubject.complete();

      // THEN
      expect(avionService.create).toHaveBeenCalledWith(avion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Avion>>();
      const avion = { id: 123 };
      jest.spyOn(avionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avionService.update).toHaveBeenCalledWith(avion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAtelierById', () => {
      it('Should return tracked Atelier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAtelierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRevisionById', () => {
      it('Should return tracked Revision primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRevisionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
