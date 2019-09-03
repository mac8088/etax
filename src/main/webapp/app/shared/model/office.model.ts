export interface IOffice {
  id?: number;
  cstdOfficeType?: string;
  name?: string;
  cstdClassifierCode?: string;
  effectiveDate?: Date;
  expiryDate?: Date;
  phone?: string;
  fax?: string;
  stl?: string;
  mngOffice?: number;
  physicalAdr?: any;
  postalAadr?: any;
  pinCode?: string;
  cstdWeekWorkingDay?: string;
  officeCode?: string;
  cstdOfficeSubType?: string;
  cstdOfficeFuncType?: string;
  ccVersion?: number;
}

export class Office implements IOffice {
  constructor(
    public id?: number,
    public cstdOfficeType?: string,
    public name?: string,
    public cstdClassifierCode?: string,
    public effectiveDate?: Date,
    public expiryDate?: Date,
    public phone?: string,
    public fax?: string,
    public stl?: string,
    public mngOffice?: number,
    public physicalAdr?: any,
    public postalAadr?: any,
    public pinCode?: string,
    public cstdWeekWorkingDay?: string,
    public officeCode?: string,
    public cstdOfficeSubType?: string,
    public cstdOfficeFuncType?: string,
    public ccVersion?: number
  ) {}
}
