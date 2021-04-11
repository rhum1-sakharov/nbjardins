import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditIntegerFieldComponent} from './edit-integer-field.component';

describe('EditIntegerFieldComponent', () => {
  let component: EditIntegerFieldComponent;
  let fixture: ComponentFixture<EditIntegerFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditIntegerFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditIntegerFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
