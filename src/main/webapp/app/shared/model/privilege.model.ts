export interface IPrivilege {
  id?: number;
  appCode?: string;
  userName?: string;
  profileName?: string;
  limit?: number;
  confirmStatus?: string;
  effectiveDate?: Date;
  expiryDate?: Date;
}

export class Privilege implements IPrivilege {
  constructor(
    public id?: number,
    public appCode?: string,
    public userName?: string,
    public profileName?: string,
    public limit?: number,
    public confirmStatus?: string,
    public effectiveDate?: Date,
    public expiryDate?: Date
  ) {}
}
