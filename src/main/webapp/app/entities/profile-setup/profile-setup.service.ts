import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProfileSetup } from '@/shared/model/profile-setup.model';

const baseApiUrl = 'api/profile-setups';

export default class ProfileSetupService {
  public find(id: number): Promise<IProfileSetup> {
    return new Promise<IProfileSetup>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity: IProfileSetup): Promise<IProfileSetup> {
    return new Promise<IProfileSetup>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IProfileSetup): Promise<IProfileSetup> {
    return new Promise<IProfileSetup>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
