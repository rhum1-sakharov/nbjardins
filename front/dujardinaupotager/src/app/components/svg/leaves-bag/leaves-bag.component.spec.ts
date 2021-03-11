import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LeavesBagComponent} from './leaves-bag.component';

describe('LeavesBagComponent', () => {
  let component: LeavesBagComponent;
  let fixture: ComponentFixture<LeavesBagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeavesBagComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeavesBagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
