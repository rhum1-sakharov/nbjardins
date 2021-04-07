import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AbandonnesComponent} from './abandonnes.component';

describe('AbandonnesComponent', () => {
  let component: AbandonnesComponent;
  let fixture: ComponentFixture<AbandonnesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AbandonnesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AbandonnesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
