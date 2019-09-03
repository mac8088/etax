import { IUser } from '@/shared/model/user.model';

export interface IUserInfo {
  id?: number;
  cstdAdmSection?: string;
  cstdSecurityLevel?: string;
  cstdUserType?: string;
  description?: string;
  middleName?: string;
  gender?: string;
  phoneNum?: string;
  faxNum?: string;
  effiectiveDate?: Date;
  expiryDate?: Date;
  blocked?: boolean;
  blockedReason?: string;
  forcedPwdChange?: boolean;
  cstdTitles?: string;
  cstdStatus?: string;
  cstdAdmDivsison?: string;
  loginStatus?: string;
  loginTime?: Date;
  attempt?: number;
  needApprove?: boolean;
  logoutTime?: Date;
  nationalId?: string;
  cstdOrganizationGrade?: string;
  cstdEmploymentType?: string;
  manuScriptContentType?: string;
  manuScript?: any;
  ccVersion?: number;
  user?: IUser;
}

export class UserInfo implements IUserInfo {
  constructor(
    public id?: number,
    public cstdAdmSection?: string,
    public cstdSecurityLevel?: string,
    public cstdUserType?: string,
    public description?: string,
    public middleName?: string,
    public gender?: string,
    public phoneNum?: string,
    public faxNum?: string,
    public effiectiveDate?: Date,
    public expiryDate?: Date,
    public blocked?: boolean,
    public blockedReason?: string,
    public forcedPwdChange?: boolean,
    public cstdTitles?: string,
    public cstdStatus?: string,
    public cstdAdmDivsison?: string,
    public loginStatus?: string,
    public loginTime?: Date,
    public attempt?: number,
    public needApprove?: boolean,
    public logoutTime?: Date,
    public nationalId?: string,
    public cstdOrganizationGrade?: string,
    public cstdEmploymentType?: string,
    public manuScriptContentType?: string,
    public manuScript?: any,
    public ccVersion?: number,
    public user?: IUser
  ) {
    this.blocked = this.blocked || false;
    this.forcedPwdChange = this.forcedPwdChange || false;
    this.needApprove = this.needApprove || false;
  }
}
