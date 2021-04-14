import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BlockEditFieldComponent} from './block-edit-field.component';

describe('BlockEditFieldComponent', () => {
  let component: BlockEditFieldComponent;
  let fixture: ComponentFixture<BlockEditFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlockEditFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BlockEditFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
