import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ParametresComponent} from './parametres.component';
import {RouterTestingModule} from '@angular/router/testing';
import {ConfirmationService, MessageService} from 'primeng/api';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {MArtisan, MArtisanBanque, MConditionReglement, MPersonne, MTaxe} from '../../../../../core-lib/dist/core-lib';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

declare const viewport: any;


describe('ParametresComponent', () => {
  let component: ParametresComponent;
  let fixture: ComponentFixture<ParametresComponent>;

  let artisan = new MArtisan();
  artisan.personne = new MPersonne();
  artisan.taxe = new MTaxe('1', 'normal', 20);
  artisan.conditionDeReglement = new MConditionReglement('1', 'à réception de la facture');
  let artisanBanque = new MArtisanBanque();

  const route = {
    data: of({
      parametresSupplier: {
        data: {
          taxeAll: [],
          conditionReglementAll: [],
          artisanByEmail: artisan,
          artisanBanqueByEmailAndPrefere: artisanBanque
        }
      }
    })
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParametresComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule
      ],
      providers: [
        MessageService,
        ConfirmationService,
        {provide: ActivatedRoute, useValue: route}
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametresComponent);

    component = fixture.componentInstance;

    fixture.detectChanges();
  });


  it('input width should be 200px when media is less than 961px ', () => {

    viewport.set(960, 600);
    const input = fixture.nativeElement.querySelector('[data-test="siret"]');

    fixture.detectChanges();

    expect(input.getBoundingClientRect().width).toEqual(200);

  });

  it('input width should be 300px when media is greater than 960px ', () => {

    viewport.set(961, 600);
    const input = fixture.nativeElement.querySelector('[data-test="siret"]');

    fixture.detectChanges();

    expect(input.getBoundingClientRect().width).toEqual(300);

  });

});
