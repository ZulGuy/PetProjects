import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RecentProjectService {
  private readonly STORAGE_KEY = 'recentProjectId';

  constructor() { }

  setRecentProject(project: { id: string; name: string; description: string }): void {
    localStorage.setItem('recentProject', JSON.stringify(project));
  }

  getRecentProject(): { id: string; name: string; description: string } | null {
    const data = localStorage.getItem('recentProject');
    return data ? JSON.parse(data) : null;
  }
}
