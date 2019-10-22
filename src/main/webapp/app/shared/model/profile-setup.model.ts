export interface IProfileSetup {
  id?: number;
  profileCode?: string;
  cstdOfficeType?: string;
  cstdUserType?: string;
}

export class ProfileSetup implements IProfileSetup {
  constructor(public id?: number, public profileCode?: string, public cstdOfficeType?: string, public cstdUserType?: string) {}
}
