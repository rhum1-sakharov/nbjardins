import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EntretienPiscineComponent} from './entretien-piscine.component';

describe('EntretienPiscineComponent', () => {
  let component: EntretienPiscineComponent;
  let fixture: ComponentFixture<EntretienPiscineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EntretienPiscineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EntretienPiscineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
