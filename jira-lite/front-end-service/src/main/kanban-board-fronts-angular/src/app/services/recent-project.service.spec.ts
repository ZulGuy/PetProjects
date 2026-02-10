import { TestBed } from '@angular/core/testing';

import { RecentProjectService } from './recent-project.service';

describe('RecentProjectService', () => {
  let service: RecentProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecentProjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
