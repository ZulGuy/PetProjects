import { TaskDTO } from '../types/api.types';

export class Column {
  constructor(public name: string, public tasks: TaskDTO[]) {}
}
