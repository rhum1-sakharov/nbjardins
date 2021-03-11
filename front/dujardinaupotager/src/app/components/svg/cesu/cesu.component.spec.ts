import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CesuComponent} from './cesu.component';

describe('CesuComponent', () => {
  let component: CesuComponent;
  let fixture: ComponentFixture<CesuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CesuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CesuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
