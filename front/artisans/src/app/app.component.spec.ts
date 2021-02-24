import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {AppComponent} from './app.component';
import {ConfirmationService, MessageService} from 'primeng/api';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {LoginComponent} from './components/login/login.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ToastModule} from 'primeng/toast';
import {TopMenuComponent} from './components/top-menu/top-menu.component';


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
        TopMenuComponent,
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

  it('content should be positionned at 85 px of the top ', () => {


    const content = fixture.nativeElement.querySelector('[data-test="content"]');
    const contentStyle = window.getComputedStyle(content);
    expect(contentStyle.getPropertyValue('top')).toEqual('85px');
    expect(contentStyle.getPropertyValue('position')).toEqual('absolute');

  });

});
