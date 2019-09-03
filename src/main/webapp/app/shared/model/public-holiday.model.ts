export interface IPublicHoliday {
  id?: number;
  cstdHolidayTypes?: string;
  description?: string;
  date?: Date;
  workingFlag?: boolean;
  countryWide?: boolean;
  ccVersion?: number;
}

export class PublicHoliday implements IPublicHoliday {
  constructor(
    public id?: number,
    public cstdHolidayTypes?: string,
    public description?: string,
    public date?: Date,
    public workingFlag?: boolean,
    public countryWide?: boolean,
    public ccVersion?: number
  ) {
    this.workingFlag = this.workingFlag || false;
    this.countryWide = this.countryWide || false;
  }
}
