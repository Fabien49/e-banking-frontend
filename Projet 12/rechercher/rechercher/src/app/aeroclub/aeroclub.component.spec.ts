import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AeroclubComponent } from './aeroclub.component';

describe('AeroclubComponent', () => {
  let component: AeroclubComponent;
  let fixture: ComponentFixture<AeroclubComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AeroclubComponent]
    });
    fixture = TestBed.createComponent(AeroclubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
