import { IUiapp } from '@/shared/model/uiapp.model';

export const enum ResourceType {
  WORKFLOW = 'WORKFLOW',
  CONTROLLER = 'CONTROLLER',
  REST = 'REST'
}

export interface IResource {
  id?: number;
  resCode?: string;
  resName?: string;
  resType?: ResourceType;
  appDesc?: string;
  cstdModule?: string;
  resContent?: string;
  uiapps?: IUiapp[];
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public resCode?: string,
    public resName?: string,
    public resType?: ResourceType,
    public appDesc?: string,
    public cstdModule?: string,
    public resContent?: string,
    public uiapps?: IUiapp[]
  ) {}
}
