import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DemandeDeDevisComponent} from './demande-de-devis.component';

describe('DemandeDeDevisComponent', () => {
  let component: DemandeDeDevisComponent;
  let fixture: ComponentFixture<DemandeDeDevisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandeDeDevisComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeDeDevisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
