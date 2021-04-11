import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditEmailFieldComponent} from './edit-email-field.component';

describe('EditEmailFieldComponent', () => {
  let component: EditEmailFieldComponent;
  let fixture: ComponentFixture<EditEmailFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditEmailFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEmailFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
