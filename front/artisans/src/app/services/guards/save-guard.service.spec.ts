import {TestBed} from '@angular/core/testing';

import {SaveGuardService} from './save-guard.service';

describe('SaveGuardService', () => {
  let service: SaveGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaveGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
