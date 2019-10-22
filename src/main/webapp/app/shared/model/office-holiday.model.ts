export interface IOfficeHoliday {
  id?: number;
  officeId?: number;
  holidayId?: string;
}

export class OfficeHoliday implements IOfficeHoliday {
  constructor(public id?: number, public officeId?: number, public holidayId?: string) {}
}
