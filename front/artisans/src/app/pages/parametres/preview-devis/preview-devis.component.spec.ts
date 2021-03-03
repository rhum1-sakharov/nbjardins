import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PreviewDevisComponent} from './preview-devis.component';

describe('PreviewDevisComponent', () => {
  let component: PreviewDevisComponent;
  let fixture: ComponentFixture<PreviewDevisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PreviewDevisComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewDevisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
