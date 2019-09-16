export interface IStdCodesProp {
  id?: number;
  groupCode?: string;
  internalCode?: string;
  propCode?: string;
  startDate?: Date;
  endDate?: Date;
  valueDate?: Date;
  valueString?: string;
  valueBool?: boolean;
  valueNumber?: number;
}

export class StdCodesProp implements IStdCodesProp {
  constructor(
    public id?: number,
    public groupCode?: string,
    public internalCode?: string,
    public propCode?: string,
    public startDate?: Date,
    public endDate?: Date,
    public valueDate?: Date,
    public valueString?: string,
    public valueBool?: boolean,
    public valueNumber?: number
  ) {
    this.valueBool = this.valueBool || false;
  }
}
