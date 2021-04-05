import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ATraiterComponent} from './a-traiter.component';

describe('ATraiterComponent', () => {
  let component: ATraiterComponent;
  let fixture: ComponentFixture<ATraiterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ATraiterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ATraiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
