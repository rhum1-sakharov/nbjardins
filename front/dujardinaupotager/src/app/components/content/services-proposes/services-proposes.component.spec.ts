import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ServicesProposesComponent} from './services-proposes.component';

describe('ServicesProposesComponent', () => {
  let component: ServicesProposesComponent;
  let fixture: ComponentFixture<ServicesProposesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServicesProposesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicesProposesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
