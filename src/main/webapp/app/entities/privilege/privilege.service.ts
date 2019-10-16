import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPrivilege } from '@/shared/model/privilege.model';

const baseApiUrl = 'api/privileges';

export default class PrivilegeService {
  public find(id: number): Promise<IPrivilege> {
    return new Promise<IPrivilege>(resolve => {
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

  public create(entity: IPrivilege): Promise<IPrivilege> {
    return new Promise<IPrivilege>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPrivilege): Promise<IPrivilege> {
    return new Promise<IPrivilege>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
