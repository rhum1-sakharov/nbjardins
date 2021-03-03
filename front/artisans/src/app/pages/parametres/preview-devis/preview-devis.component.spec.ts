import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PreviewDevisComponent} from './preview-devis.component';
import {MArtisan, MArtisanBanque, MPersonne} from 'rhum1-sakharov-core-lib';

describe('PreviewDevisComponent', () => {
  let component: PreviewDevisComponent;
  let fixture: ComponentFixture<PreviewDevisComponent>;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreviewDevisComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewDevisComponent);
    component = fixture.componentInstance;

    component.artisan = new MArtisan();
    component.artisan.personne = new MPersonne();

    component.artisanBanque = new MArtisanBanque();

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have provision paragraph if {{artisan.provision}} greater than 0', () => {

    component.artisan.provision=1;
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('[data-test="provision"]')).toBeTruthy();
  });

  it('should not have provision paragraph if {{artisan.provision}} <= 0', () => {

    component.artisan.provision=0;
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('[data-test="provision"]')).toBeFalsy();
  });



});
