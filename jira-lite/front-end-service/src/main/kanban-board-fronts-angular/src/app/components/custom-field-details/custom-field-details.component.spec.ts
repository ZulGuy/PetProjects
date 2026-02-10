import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomFieldDetailsComponent } from './custom-field-details.component';

describe('CustomFieldDetailsComponent', () => {
  let component: CustomFieldDetailsComponent;
  let fixture: ComponentFixture<CustomFieldDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomFieldDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomFieldDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
