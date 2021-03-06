import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TopMenuComponent} from './top-menu.component';
import {LoginComponent} from '../login/login.component';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {ConfirmationService, MessageService} from 'primeng/api';
import {ToastModule} from 'primeng/toast';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {RouterTestingModule} from '@angular/router/testing';

declare const viewport: any;

describe('TopMenuComponent', () => {

  let component: TopMenuComponent;
  let fixture: ComponentFixture<TopMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        TopMenuComponent,
        LoginComponent
      ],
      imports: [
        RouterTestingModule,
        ToastModule,
        ConfirmDialogModule
      ],
      providers:[
        ConfirmationService,
        HttpClient,
        HttpHandler,
        MessageService
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TopMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have menu', () => {
    expect(fixture.nativeElement.querySelector('[data-test="menu"]')).toBeTruthy();
  });

  it('should have logo', () => {
    expect(fixture.nativeElement.querySelector('[data-test="logo"]')).toBeTruthy();
  });

  it('should have menu parametres', () => {
    expect(fixture.nativeElement.querySelector('[data-test="parametres"]')).toBeTruthy();
  });

  it('should have menu devis', () => {
    expect(fixture.nativeElement.querySelector('[data-test="devis"]')).toBeTruthy();
  });

  it('should have menu factures', () => {
    expect(fixture.nativeElement.querySelector('[data-test="factures"]')).toBeTruthy();
  });

  it('should have menu stats', () => {
    expect(fixture.nativeElement.querySelector('[data-test="stats"]')).toBeTruthy();
  });


  it('should have menu compte', () => {
    expect(fixture.nativeElement.querySelector('[data-test="compte"]')).toBeTruthy();
  });


  it('logo width should be 40px when media is less than 961px ', () => {

    viewport.set(960, 600);
    const logoComponent = fixture.nativeElement.querySelector('[data-test="logo"]');

    expect(logoComponent.getBoundingClientRect().width).toEqual(40);

  });

  it('logo width should be 150px when media is greater than 960px ', () => {

    viewport.set(961, 600);
    const logoComponent = fixture.nativeElement.querySelector('[data-test="logo"]');

    expect(logoComponent.getBoundingClientRect().width).toEqual(150);

  });

  it('parametres, devis and factures should disappear when media is less than 961px ', () => {

    viewport.set(960, 600);
    const parametresComponent = fixture.nativeElement.querySelector('[data-test="parametres"]');
    const devisComponent = fixture.nativeElement.querySelector('[data-test="devis"]');
    const facturesComponent = fixture.nativeElement.querySelector('[data-test="factures"]');

    const parametresStyle = window.getComputedStyle(parametresComponent);
    const devisStyle = window.getComputedStyle(devisComponent);
    const facturesStyle = window.getComputedStyle(facturesComponent);

    expect(parametresStyle.getPropertyValue('display')).toEqual('none');
    expect(devisStyle.getPropertyValue('display')).toEqual('none');
    expect(facturesStyle.getPropertyValue('display')).toEqual('none');

  });

  it('3 bars buttons should disappear when media is greater than 960px ', () => {

    viewport.set(961, 600);
    const threeBarButton = fixture.nativeElement.querySelector('[data-test="threeBarButton"]');

    const threeBarButtonStyle = window.getComputedStyle(threeBarButton);

    expect(threeBarButtonStyle.getPropertyValue('display')).toEqual('none');

  });

  it('menu should have 70px height ', () => {

    const menuComponent = fixture.nativeElement.querySelector('[data-test="menu"]');


    expect(menuComponent.getBoundingClientRect().height).toEqual(70);


  });

});
