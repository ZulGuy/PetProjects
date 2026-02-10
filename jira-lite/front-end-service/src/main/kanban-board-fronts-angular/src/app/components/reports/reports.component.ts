import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../../services/task.service';
import { TaskDTO } from '../../types/api.types';
import { ChartData, ChartType } from 'chart.js';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit, OnDestroy {
  tasks: TaskDTO[] = [];
  statusCounts = { TODO: 0, IN_PROGRESS: 0, DONE: 0 };

  public doughnutChartLabels = ['TODO', 'IN_PROGRESS', 'DONE'];
  public doughnutChartData: number[] = [];
  public doughnutChartType: ChartType = 'doughnut';
  public doughnutChartColors = [
    {
      backgroundColor: ['#b0b0b0', '#1976d2', '#43a047'], // grey, blue, green
      borderColor: '#fff'
    }
  ];

  private routeSub!: Subscription;

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.routeSub = this.route.parent?.paramMap.subscribe(params => {
      const projectId = +params.get('id')!;
      console.log('Проект:', projectId);
      if (!isNaN(projectId)) {
        this.loadTasks(projectId);
      }
    });
  }

  loadTasks(projectId: number) {
    this.taskService.getTasksForProject(projectId).subscribe(tasks => {
      this.tasks = tasks;
      this.countStatuses();
      this.updateChart();
    });
  }

  countStatuses() {
    this.statusCounts = { TODO: 0, IN_PROGRESS: 0, DONE: 0 };
    this.tasks.forEach(t => this.statusCounts[t.status]++);
  }

  updateChart() {
    this.doughnutChartData = [
      this.statusCounts.TODO,
      this.statusCounts.IN_PROGRESS,
      this.statusCounts.DONE
    ];
  }

  ngOnDestroy(): void {
    this.routeSub.unsubscribe();
  }
}
