import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import {ProjectDTO, TaskDTO} from '../types/api.types';
import {BehaviorSubject} from "rxjs";
import {filter} from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class ProjectService {
  private api = `${environment.apiUrl}/api/projects`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProjectDTO[]> {
    return this.http.get<ProjectDTO[]>(this.api, { withCredentials: true });
  }

  getById(id: number): Observable<ProjectDTO> {
    return this.http.get<ProjectDTO>(`${this.api}/${id}`, { withCredentials: true });
  }

  private currentProject = new BehaviorSubject<ProjectDTO | null>(null);

  setProject(project: ProjectDTO) {
    this.currentProject.next(project);
  }

  updateTask(task: TaskDTO): Observable<TaskDTO> {
    return this.http.put<TaskDTO>(`${this.api}/tasks/${task.id}`, task);
  }

  getProject(): Observable<ProjectDTO> {
    return this.currentProject.asObservable().pipe(
      filter((p): p is ProjectDTO => p !== null)
    );
  }

  getTasksForProject(projectId: number): Observable<TaskDTO[]> {
    return this.http.get<TaskDTO[]>(`${this.api}/tasks/project/${projectId}`);
  }

  createProject(project: { name: string; description: string }): Observable<ProjectDTO> {
    return this.http.post<ProjectDTO>(`${this.api}`, project, { withCredentials: true });
  }

  deleteProject(projectId: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${projectId}`, { withCredentials: true });
  }
}
