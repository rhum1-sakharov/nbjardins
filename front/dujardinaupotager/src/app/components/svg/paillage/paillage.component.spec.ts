import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PaillageComponent} from './paillage.component';

describe('PaillageComponent', () => {
  let component: PaillageComponent;
  let fixture: ComponentFixture<PaillageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaillageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaillageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
