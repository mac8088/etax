import { IStdCodes } from '@/shared/model/std-codes.model';

export const enum OptionIndicator {
  Y = 'Y',
  N = 'N'
}

export const enum ValueTypeIndicator {
  D = 'D',
  S = 'S',
  N = 'N',
  B = 'B'
}

export interface IStdCodesGroup {
  id?: number;
  groupCode?: string;
  groupDesc?: string;
  systemInd?: OptionIndicator;
  secLevelRequired?: OptionIndicator;
  valueRequired?: OptionIndicator;
  valueType?: ValueTypeIndicator;
  descriptionRequired?: OptionIndicator;
  externalCodeRequired?: OptionIndicator;
  externalCodeLength?: number;
  parentGroupRequired?: OptionIndicator;
  parentGroupCode?: string;
  touppercase?: boolean;
  stdCodes?: IStdCodes[];
}

export class StdCodesGroup implements IStdCodesGroup {
  constructor(
    public id?: number,
    public groupCode?: string,
    public groupDesc?: string,
    public systemInd?: OptionIndicator,
    public secLevelRequired?: OptionIndicator,
    public valueRequired?: OptionIndicator,
    public valueType?: ValueTypeIndicator,
    public descriptionRequired?: OptionIndicator,
    public externalCodeRequired?: OptionIndicator,
    public externalCodeLength?: number,
    public parentGroupRequired?: OptionIndicator,
    public parentGroupCode?: string,
    public touppercase?: boolean,
    public stdCodes?: IStdCodes[]
  ) {
    this.touppercase = this.touppercase || false;
  }
}
