import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditDecimalFieldComponent} from './edit-decimal-field.component';

describe('EditDecimalFieldComponent', () => {
  let component: EditDecimalFieldComponent;
  let fixture: ComponentFixture<EditDecimalFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditDecimalFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditDecimalFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
