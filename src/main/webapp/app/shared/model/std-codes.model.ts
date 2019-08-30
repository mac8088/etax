import { IStdCodesDesc } from '@/shared/model/std-codes-desc.model';
import { IStdCodesGroup } from '@/shared/model/std-codes-group.model';

export interface IStdCodes {
  id?: number;
  groupCode?: string;
  internalCode?: string;
  startDate?: Date;
  endDate?: Date;
  parentInternalCode?: string;
  comments?: string;
  secLevel?: number;
  codeValueDate?: Date;
  codeValueString?: string;
  codeValueBool?: boolean;
  codeValueNumber?: number;
  stdCodesDescs?: IStdCodesDesc[];
  stdCodesGroup?: IStdCodesGroup;
}

export class StdCodes implements IStdCodes {
  constructor(
    public id?: number,
    public groupCode?: string,
    public internalCode?: string,
    public startDate?: Date,
    public endDate?: Date,
    public parentInternalCode?: string,
    public comments?: string,
    public secLevel?: number,
    public codeValueDate?: Date,
    public codeValueString?: string,
    public codeValueBool?: boolean,
    public codeValueNumber?: number,
    public stdCodesDescs?: IStdCodesDesc[],
    public stdCodesGroup?: IStdCodesGroup
  ) {
    this.codeValueBool = this.codeValueBool || false;
  }
}
