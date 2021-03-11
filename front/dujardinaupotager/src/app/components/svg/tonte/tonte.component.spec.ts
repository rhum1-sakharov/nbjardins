import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TonteComponent} from './tonte.component';

describe('TonteComponent', () => {
  let component: TonteComponent;
  let fixture: ComponentFixture<TonteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TonteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TonteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
