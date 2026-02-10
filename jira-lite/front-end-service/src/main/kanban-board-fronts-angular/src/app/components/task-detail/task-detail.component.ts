import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../../services/task.service';
import { UserService } from '../../services/user.service';
import { CommentService } from '../../services/comment.service';
import { TaskDTO, UserDTO, CommentDTO } from '../../types/api.types';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html'
})
export class TaskDetailComponent implements OnInit {
  task!: TaskDTO;
  taskId!: number;
  users: UserDTO[] = [];
  comments: CommentDTO[] = [];
  newComment = '';
  canEdit = false;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private userService: UserService,
    private commentService: CommentService,
    public authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.taskId = +this.route.snapshot.paramMap.get('id')!;
    this.loadTask();
    this.loadUsers();
    this.loadComments();

  }

  loadTask() {
    this.taskService.getTaskById(this.taskId).subscribe(t => {
      this.task = t;

      this.authService.canEditTask(t.projectId).subscribe(canEdit => {
        this.canEdit = canEdit;
      });
    });
  }


  loadUsers() {
    this.userService.getAll().subscribe(users => this.users = users);
  }

  loadComments() {
    this.commentService.getCommentsForTask(this.taskId).subscribe(data => {
      console.log('Коментарі з сервера:', data); // Додай це
      this.comments = data;
    });
  }

  save() {
    this.taskService.updateTask(this.task).subscribe(() => alert('Збережено!'));
  }

  addComment() {
    if (!this.newComment.trim()) return;
    this.commentService.addCommentToTask(this.taskId, this.newComment)
    .subscribe(() => {
      this.newComment = '';
      this.loadComments();
    });
  }

  deleteComment(id: number) {
    if (confirm('Ви впевнені, що хочете видалити цей коментар?')) {
      this.commentService.deleteComment(this.taskId, id).subscribe(() => {
        this.loadComments();
      });
    }
  }

  editComment(comment: CommentDTO) {
    const updated = prompt('Редагувати коментар:', comment.description);
    if (updated !== null) {
      this.commentService.updateComment(this.taskId, { ...comment, description: updated }).subscribe(() => {
        this.loadComments();
      });
    }
  }

}
