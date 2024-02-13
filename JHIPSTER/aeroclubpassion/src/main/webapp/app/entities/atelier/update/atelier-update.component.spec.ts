jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AtelierService } from '../service/atelier.service';
import { IAtelier, Atelier } from '../atelier.model';

import { AtelierUpdateComponent } from './atelier-update.component';

describe('Atelier Management Update Component', () => {
  let comp: AtelierUpdateComponent;
  let fixture: ComponentFixture<AtelierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let atelierService: AtelierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AtelierUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AtelierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AtelierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    atelierService = TestBed.inject(AtelierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const atelier: IAtelier = { id: 456 };

      activatedRoute.data = of({ atelier });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(atelier));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Atelier>>();
      const atelier = { id: 123 };
      jest.spyOn(atelierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atelier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atelier }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(atelierService.update).toHaveBeenCalledWith(atelier);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Atelier>>();
      const atelier = new Atelier();
      jest.spyOn(atelierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atelier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atelier }));
      saveSubject.complete();

      // THEN
      expect(atelierService.create).toHaveBeenCalledWith(atelier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Atelier>>();
      const atelier = { id: 123 };
      jest.spyOn(atelierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atelier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(atelierService.update).toHaveBeenCalledWith(atelier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
