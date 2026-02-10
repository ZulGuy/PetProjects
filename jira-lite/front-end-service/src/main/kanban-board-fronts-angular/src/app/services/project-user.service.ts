import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { ProjectUserDTO, ProjectRole } from '../types/api.types';

@Injectable({ providedIn: 'root' })
export class ProjectUserService {
  private api = `${environment.apiUrl}/api/projects`;

  constructor(private http: HttpClient) {}

  getProjectUsers(projectId: number): Observable<ProjectUserDTO[]> {
    return this.http.get<ProjectUserDTO[]>(
      `${this.api}/${projectId}/users`,
      { withCredentials: true}
    );
  }

  addUserToProject(projectId: number, userId: number, roles: ProjectRole[]): Observable<void> {
    return this.http.post<void>(
      `${this.api}/${projectId}/users`,
      { userId, roles },
      { withCredentials: true}
    );
  }


  updateRole(projectId: number, userId: number, role: ProjectRole | null): Observable<void> {
    const roles = role ? [role] : [];
    return this.http.put<void>(
      `${this.api}/${projectId}/users/${userId}/roles`,
      { roles },
      { withCredentials: true }
    );
  }

  removeUserFromProject(projectId: number, userId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.api}/${projectId}/users/${userId}`,
      { withCredentials: true}
    );
  }

}
