import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RefusesComponent} from './refuses.component';

describe('RefusesComponent', () => {
  let component: RefusesComponent;
  let fixture: ComponentFixture<RefusesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefusesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefusesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
