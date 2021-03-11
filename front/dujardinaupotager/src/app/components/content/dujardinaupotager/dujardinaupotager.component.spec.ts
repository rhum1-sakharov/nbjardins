import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DujardinaupotagerComponent} from './dujardinaupotager.component';

describe('DujardinaupotagerComponent', () => {
  let component: DujardinaupotagerComponent;
  let fixture: ComponentFixture<DujardinaupotagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DujardinaupotagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DujardinaupotagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
