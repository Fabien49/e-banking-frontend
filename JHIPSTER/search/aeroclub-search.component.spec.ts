import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AeroclubSearchComponent } from './aeroclub-search.component';

describe('Aeroclub Management Search Component', () => {
  let comp: AeroclubSearchComponent;
  let fixture: ComponentFixture<AeroclubSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AeroclubSearchComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ aeroclub: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AeroclubSearchComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AeroclubSearchComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load aeroclub on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.aeroclub).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
