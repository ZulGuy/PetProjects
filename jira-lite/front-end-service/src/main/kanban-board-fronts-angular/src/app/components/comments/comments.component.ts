import { Component, Input, OnInit } from '@angular/core';
import { CommentService } from '../../services/comment.service';

@Component({
  selector: 'app-comments',
  template: `
    <div *ngIf="taskId">
      <ul>
        <li *ngFor="let comment of comments">{{ comment.description }}</li>
      </ul>
      <form (ngSubmit)="add()">
        <input [(ngModel)]="newComment" name="comment" required>
        <button type="submit">Add</button>
      </form>
    </div>
  `
})
export class CommentsComponent implements OnInit {
  @Input() taskId!: number;
  comments: any[] = [];
  newComment = '';

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    this.loadComments();
  }

  loadComments() {
    this.commentService.getByTaskId(this.taskId).subscribe(comments => this.comments = comments);
  }

  add() {
    this.commentService.addComment(this.taskId, this.newComment)
    .subscribe(() => {
      this.newComment = '';
      this.loadComments();
    });
  }
}
