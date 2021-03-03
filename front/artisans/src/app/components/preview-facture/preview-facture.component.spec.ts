import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PreviewFactureComponent} from './preview-facture.component';

describe('PreviewFactureComponent', () => {
  let component: PreviewFactureComponent;
  let fixture: ComponentFixture<PreviewFactureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PreviewFactureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewFactureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
