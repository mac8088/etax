import { IStdCodes } from '@/shared/model/std-codes.model';

export interface IStdCodesDesc {
  id?: number;
  groupCode?: string;
  internalCode?: string;
  langCode?: string;
  startDate?: Date;
  endDate?: Date;
  externalCode?: string;
  codeDesc?: string;
  stdCodes?: IStdCodes;
}

export class StdCodesDesc implements IStdCodesDesc {
  constructor(
    public id?: number,
    public groupCode?: string,
    public internalCode?: string,
    public langCode?: string,
    public startDate?: Date,
    public endDate?: Date,
    public externalCode?: string,
    public codeDesc?: string,
    public stdCodes?: IStdCodes
  ) {}
}
