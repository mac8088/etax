import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IOfficeTaxFunc } from '@/shared/model/office-tax-func.model';

const baseApiUrl = 'api/office-tax-funcs';

export default class OfficeTaxFuncService {
  public find(id: number): Promise<IOfficeTaxFunc> {
    return new Promise<IOfficeTaxFunc>(resolve => {
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

  public create(entity: IOfficeTaxFunc): Promise<IOfficeTaxFunc> {
    return new Promise<IOfficeTaxFunc>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IOfficeTaxFunc): Promise<IOfficeTaxFunc> {
    return new Promise<IOfficeTaxFunc>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
