import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {TaskDTO} from "../types/api.types";


@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private api = `${environment.apiUrl}/api/tasks`;

  constructor(private http: HttpClient) {}

  getTasksForProject(projectId: number): Observable<TaskDTO[]> {
    return this.http.get<TaskDTO[]>(`${this.api}/project/${projectId}`, { withCredentials: true });
  }

  getTaskById(id: number): Observable<TaskDTO> {
    return this.http.get<TaskDTO>(`${this.api}/${id}`, { withCredentials: true });
  }

  createTask(task: Partial<TaskDTO>): Observable<TaskDTO> {
    return this.http.post<TaskDTO>(this.api, task, { withCredentials: true });
  }

  updateTask(task: TaskDTO): Observable<TaskDTO> {
    return this.http.put<TaskDTO>(`${this.api}/${task.id}`, task, { withCredentials: true });
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`, { withCredentials: true });
  }
}
