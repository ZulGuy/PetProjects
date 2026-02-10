import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { TaskService } from '../../services/task.service';
import {TaskDTO, TaskStatus, UserDTO} from '../../types/api.types';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-all-tasks',
  templateUrl: './all-tasks.component.html',
  styleUrls: ['./all-tasks.component.scss']
})
export class AllTasksComponent implements OnInit {
  projectId!: number;
  allTasks: TaskDTO[] = [];
  filteredTasks: TaskDTO[] = [];
  users: UserDTO[] = [];

  filters = {
    summary: '',
    description: '',
    assignee: '',
    reporter: '',
    status: '' as TaskStatus | ''
  };

  currentPage = 1;
  itemsPerPage = 10;

  constructor(private taskService: TaskService, private route: ActivatedRoute, private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.route.parent?.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.projectId = +id;
        this.loadTasks();
      }
    });
    this.loadUsers();
  }

  loadTasks() {
    this.taskService.getTasksForProject(this.projectId).subscribe(data => {
      this.allTasks = data;
      this.applyFilters();
    });
  }

  applyFilters() {
    const { summary, description, assignee, reporter, status } = this.filters;

    this.filteredTasks = this.allTasks.filter(task => {
      return (!summary || task.summary?.toLowerCase().includes(summary.toLowerCase())) &&
        (!description || task.description?.toLowerCase().includes(description.toLowerCase())) &&
        (!assignee || task.assignee?.name?.toLowerCase().includes(assignee.toLowerCase())) &&
        (!reporter || task.initiator?.name?.toLowerCase().includes(reporter.toLowerCase())) &&
        (!status || task.status === status);
    });

    this.currentPage = 1;
  }

  get paginatedTasks(): TaskDTO[] {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredTasks.slice(start, start + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredTasks.length / this.itemsPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }

  prevPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  loadUsers() {
    this.userService.getAll().subscribe(users => this.users = users);
  }

  getUserNameById(id: number | null | undefined): string {
    if (!id) return '—';
    const user = this.users.find(u => u.id === id);
    return user ? user.name || user.email : '—';
  }

  goToTask(taskId: number) {
    // Якщо у вас проектId є у роуті, тоді треба вставити і його:
    // this.router.navigate(['../task', taskId], { relativeTo: this.route });
    // або:
    this.router.navigate(['/task', taskId]);
  }

}
