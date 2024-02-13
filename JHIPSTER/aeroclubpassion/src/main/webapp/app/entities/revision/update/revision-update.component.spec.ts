jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RevisionService } from '../service/revision.service';
import { IRevision, Revision } from '../revision.model';

import { RevisionUpdateComponent } from './revision-update.component';

describe('Revision Management Update Component', () => {
  let comp: RevisionUpdateComponent;
  let fixture: ComponentFixture<RevisionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let revisionService: RevisionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RevisionUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(RevisionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RevisionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    revisionService = TestBed.inject(RevisionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const revision: IRevision = { id: 456 };

      activatedRoute.data = of({ revision });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(revision));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Revision>>();
      const revision = { id: 123 };
      jest.spyOn(revisionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ revision });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: revision }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(revisionService.update).toHaveBeenCalledWith(revision);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Revision>>();
      const revision = new Revision();
      jest.spyOn(revisionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ revision });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: revision }));
      saveSubject.complete();

      // THEN
      expect(revisionService.create).toHaveBeenCalledWith(revision);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Revision>>();
      const revision = { id: 123 };
      jest.spyOn(revisionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ revision });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(revisionService.update).toHaveBeenCalledWith(revision);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
