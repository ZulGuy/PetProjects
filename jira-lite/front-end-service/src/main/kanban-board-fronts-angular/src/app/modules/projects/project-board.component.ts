import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from "../../services/project.service";
import {ProjectDTO} from "../../types/api.types";

@Component({
  selector: 'app-projects-board',
  templateUrl: './project-board.component.html',
  styleUrls: ['./project-board.component.scss']
})
export class ProjectBoardComponent implements OnInit {
  project: ProjectDTO | null = null;

  constructor(private route: ActivatedRoute, private projectService: ProjectService) {}

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.projectService.getById(id).subscribe(data => this.project = data);
    this.projectService.setProject(this.project);
  }
}
