export interface IOfficeTaxFunc {
  id?: number;
  taxOfficeId?: number;
  funcOfficeId?: number;
  startDate?: Date;
  endDate?: Date;
}

export class OfficeTaxFunc implements IOfficeTaxFunc {
  constructor(
    public id?: number,
    public taxOfficeId?: number,
    public funcOfficeId?: number,
    public startDate?: Date,
    public endDate?: Date
  ) {}
}
