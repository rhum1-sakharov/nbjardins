import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AcceptesComponent} from './acceptes.component';

describe('AcceptesComponent', () => {
  let component: AcceptesComponent;
  let fixture: ComponentFixture<AcceptesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcceptesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AcceptesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
