import { IOperation } from '@/shared/model/operation.model';
import { IUser } from '@/shared/model/user.model';

export interface IBankAccount {
  id?: number;
  name?: string;
  balance?: number;
  operations?: IOperation[];
  user?: IUser;
}

export class BankAccount implements IBankAccount {
  constructor(public id?: number, public name?: string, public balance?: number, public operations?: IOperation[], public user?: IUser) {}
}
