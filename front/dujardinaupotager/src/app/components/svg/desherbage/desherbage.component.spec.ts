import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DesherbageComponent} from './desherbage.component';

describe('DesherbageComponent', () => {
  let component: DesherbageComponent;
  let fixture: ComponentFixture<DesherbageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DesherbageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DesherbageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
