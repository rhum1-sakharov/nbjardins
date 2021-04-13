import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BlockArtisanComponent} from './block-artisan.component';

describe('BlockArtisanComponent', () => {
  let component: BlockArtisanComponent;
  let fixture: ComponentFixture<BlockArtisanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlockArtisanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BlockArtisanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
