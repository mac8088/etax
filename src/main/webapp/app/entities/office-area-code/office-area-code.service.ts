import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IOfficeAreaCode } from '@/shared/model/office-area-code.model';

const baseApiUrl = 'api/office-area-codes';

export default class OfficeAreaCodeService {
  public find(id: number): Promise<IOfficeAreaCode> {
    return new Promise<IOfficeAreaCode>(resolve => {
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

  public create(entity: IOfficeAreaCode): Promise<IOfficeAreaCode> {
    return new Promise<IOfficeAreaCode>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IOfficeAreaCode): Promise<IOfficeAreaCode> {
    return new Promise<IOfficeAreaCode>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
