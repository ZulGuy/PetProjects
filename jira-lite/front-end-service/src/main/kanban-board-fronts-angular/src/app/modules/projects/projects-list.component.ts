import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ProjectService } from '../../services/project.service';
import { RecentProjectService } from '../../services/recent-project.service';
import { ProjectDTO } from "../../types/api.types";

@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectsListComponent implements OnInit {
  projects: ProjectDTO[] = [];
  showCreateProjectModal = false;

  constructor(
    private projectService: ProjectService,
    private recentProjectService: RecentProjectService,
    private router: Router
  ) {}

  ngOnInit() {
    this.projectService.getAll().subscribe(data => this.projects = data);
  }

  openProject(project: ProjectDTO) {
    this.recentProjectService.setRecentProject({
      id: String(project.id),
      name: project.name,
      description: project.description
    });

    this.router.navigate(['/projects', project.id]);
  }

  createProject() {
    const name = prompt('Назва проєкту?');
    const description = prompt('Опис проєкту?');

    if (name) {
      this.projectService.createProject({ name, description: description || '' })
      .subscribe(() => {
        this.projectService.getAll().subscribe(data => this.projects = data);
      });
    }
  }

  deleteProject(project: ProjectDTO, event: MouseEvent) {
    event.stopPropagation();

    if (confirm(`Ви дійсно хочете видалити проєкт "${project.name}"?`)) {
      this.projectService.deleteProject(project.id).subscribe(() => {
        this.projects = this.projects.filter(p => p.id !== project.id);
      });
    }
  }

  handleProjectCreated(event: { name: string, description: string }) {
    this.projectService.createProject({ name: event.name, description: event.description })
    .subscribe(() => {
      this.projectService.getAll().subscribe(data => this.projects = data);
    });
  }


}
