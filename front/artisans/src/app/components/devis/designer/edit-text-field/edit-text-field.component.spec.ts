import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditTextFieldComponent} from './edit-text-field.component';

describe('EditTextFieldComponent', () => {
  let component: EditTextFieldComponent;
  let fixture: ComponentFixture<EditTextFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTextFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTextFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
