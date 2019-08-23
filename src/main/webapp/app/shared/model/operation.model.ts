import { ILabel } from '@/shared/model/label.model';
import { IBankAccount } from '@/shared/model/bank-account.model';

export interface IOperation {
  id?: number;
  date?: Date;
  description?: string;
  amount?: number;
  labels?: ILabel[];
  bankAccount?: IBankAccount;
}

export class Operation implements IOperation {
  constructor(
    public id?: number,
    public date?: Date,
    public description?: string,
    public amount?: number,
    public labels?: ILabel[],
    public bankAccount?: IBankAccount
  ) {}
}
