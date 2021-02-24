import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ParametresComponent} from './parametres.component';
import {RouterTestingModule} from '@angular/router/testing';

describe('ParametresComponent', () => {
  let component: ParametresComponent;
  let fixture: ComponentFixture<ParametresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParametresComponent ],
      imports:[
        RouterTestingModule
      ],
      providers:[

      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });



  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // it('should have taxe element', () => {
  //   expect(fixture.nativeElement.querySelector('[data-test="taxe"]')).toBeTruthy();
  // });


});
