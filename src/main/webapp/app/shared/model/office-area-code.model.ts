export interface IOfficeAreaCode {
  id?: number;
  officeId?: number;
  fromPin?: string;
  toPin?: string;
}

export class OfficeAreaCode implements IOfficeAreaCode {
  constructor(public id?: number, public officeId?: number, public fromPin?: string, public toPin?: string) {}
}
