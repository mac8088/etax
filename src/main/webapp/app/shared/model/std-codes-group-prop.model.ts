export const enum ValueTypeIndicator {
  D = 'D',
  S = 'S',
  N = 'N',
  B = 'B'
}

export const enum OptionIndicator {
  Y = 'Y',
  N = 'N'
}

export interface IStdCodesGroupProp {
  id?: number;
  groupCode?: string;
  propCode?: string;
  propDesc?: string;
  startDate?: Date;
  endDate?: Date;
  propType?: ValueTypeIndicator;
  propMdtr?: OptionIndicator;
  dfltValueDate?: Date;
  dfltValueString?: string;
  dfltValueBool?: boolean;
  dfltValueNumber?: number;
}

export class StdCodesGroupProp implements IStdCodesGroupProp {
  constructor(
    public id?: number,
    public groupCode?: string,
    public propCode?: string,
    public propDesc?: string,
    public startDate?: Date,
    public endDate?: Date,
    public propType?: ValueTypeIndicator,
    public propMdtr?: OptionIndicator,
    public dfltValueDate?: Date,
    public dfltValueString?: string,
    public dfltValueBool?: boolean,
    public dfltValueNumber?: number
  ) {
    this.dfltValueBool = this.dfltValueBool || false;
  }
}
