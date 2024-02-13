import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserRegisteredDetailComponent } from './user-registered-detail.component';

describe('UserRegistered Management Detail Component', () => {
  let comp: UserRegisteredDetailComponent;
  let fixture: ComponentFixture<UserRegisteredDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserRegisteredDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userRegistered: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserRegisteredDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserRegisteredDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userRegistered on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userRegistered).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
