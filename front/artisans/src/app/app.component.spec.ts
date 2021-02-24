import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {AppComponent} from './app.component';
import {ConfirmationService, MessageService} from 'primeng/api';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {LoginComponent} from './pages/login/login.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ToastModule} from 'primeng/toast';

declare const viewport: any;

describe('AppComponent', () => {

  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        ToastModule,
        ConfirmDialogModule

      ],
      declarations: [
        AppComponent,
        LoginComponent
      ],
      providers: [
        ConfirmationService,
        HttpClient,
        HttpHandler,
        MessageService
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should create the app', () => {
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

  it('should have menu compte', () => {
    expect(fixture.nativeElement.querySelector('[data-test="compte"]')).toBeTruthy();
  });


  it('logo width should be 40px when media is less than 961px ', () => {

    viewport.set(960, 320);
    const logoComponent = fixture.nativeElement.querySelector('[data-test="logo"]');

    expect(logoComponent.getBoundingClientRect().width).toEqual(40);

  });

  it('logo width should be 150px when media is greater than 960px ', () => {

    viewport.set(961, 320);
    const logoComponent = fixture.nativeElement.querySelector('[data-test="logo"]');

    expect(logoComponent.getBoundingClientRect().width).toEqual(150);

  });

  it('parametres, devis and factures should disappear when media is less than 961px ', () => {

    viewport.set(960, 320);
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

    viewport.set(961, 320);
    const threeBarButton = fixture.nativeElement.querySelector('[data-test="threeBarButton"]');

    const threeBarButtonStyle = window.getComputedStyle(threeBarButton);

    expect(threeBarButtonStyle.getPropertyValue('display')).toEqual('none');

  });

});
