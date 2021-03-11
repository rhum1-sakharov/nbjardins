import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ArrosoirComponent} from './arrosoir.component';

describe('ArrosoirComponent', () => {
  let component: ArrosoirComponent;
  let fixture: ComponentFixture<ArrosoirComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArrosoirComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArrosoirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
