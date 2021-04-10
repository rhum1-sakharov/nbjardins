import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditDateFieldComponent} from './edit-date-field.component';

describe('EditDateFieldComponent', () => {
  let component: EditDateFieldComponent;
  let fixture: ComponentFixture<EditDateFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditDateFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditDateFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
