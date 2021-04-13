import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BlockClientComponent} from './block-client.component';

describe('BlockClientComponent', () => {
  let component: BlockClientComponent;
  let fixture: ComponentFixture<BlockClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlockClientComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BlockClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
