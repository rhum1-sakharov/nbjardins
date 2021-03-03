import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ParametresComponent} from './parametres.component';
import {RouterTestingModule} from '@angular/router/testing';
import {ConfirmationService, MessageService} from 'primeng/api';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('ParametresComponent', () => {
  let component: ParametresComponent;
  let fixture: ComponentFixture<ParametresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParametresComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule
      ],
      providers: [
        MessageService,
        ConfirmationService
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });




});
