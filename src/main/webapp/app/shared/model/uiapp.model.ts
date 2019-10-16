import { IResource } from '@/shared/model/resource.model';

export interface IUiapp {
  id?: number;
  appCode?: string;
  appName?: string;
  appDesc?: string;
  cstdModule?: string;
  appMessage?: string;
  resources?: IResource[];
}

export class Uiapp implements IUiapp {
  constructor(
    public id?: number,
    public appCode?: string,
    public appName?: string,
    public appDesc?: string,
    public cstdModule?: string,
    public appMessage?: string,
    public resources?: IResource[]
  ) {}
}
