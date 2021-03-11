import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CrudBanqueComponent} from './crud-banque.component';

describe('CrudBanqueComponent', () => {
  let component: CrudBanqueComponent;
  let fixture: ComponentFixture<CrudBanqueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrudBanqueComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrudBanqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
