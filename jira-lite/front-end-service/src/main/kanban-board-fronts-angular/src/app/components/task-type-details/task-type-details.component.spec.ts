import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskTypeDetailsComponent } from './task-type-details.component';

describe('TaskTypeDetailsComponent', () => {
  let component: TaskTypeDetailsComponent;
  let fixture: ComponentFixture<TaskTypeDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TaskTypeDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskTypeDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
