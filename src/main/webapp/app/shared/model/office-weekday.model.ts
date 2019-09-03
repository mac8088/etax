export interface IOfficeWeekday {
  id?: number;
  officeId?: number;
  cstdWeekworkingDay?: string;
  startDate?: Date;
  endDate?: Date;
}

export class OfficeWeekday implements IOfficeWeekday {
  constructor(
    public id?: number,
    public officeId?: number,
    public cstdWeekworkingDay?: string,
    public startDate?: Date,
    public endDate?: Date
  ) {}
}
