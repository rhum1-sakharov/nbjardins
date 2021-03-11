import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MeContacterComponent} from './me-contacter.component';

describe('MeContacterComponent', () => {
  let component: MeContacterComponent;
  let fixture: ComponentFixture<MeContacterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeContacterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MeContacterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
