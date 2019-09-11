import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IStdCodesGroupProp } from '@/shared/model/std-codes-group-prop.model';

const baseApiUrl = 'api/std-codes-group-props';

export default class StdCodesGroupPropService {
  public find(id: number): Promise<IStdCodesGroupProp> {
    return new Promise<IStdCodesGroupProp>(resolve => {
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

  public create(entity: IStdCodesGroupProp): Promise<IStdCodesGroupProp> {
    return new Promise<IStdCodesGroupProp>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IStdCodesGroupProp): Promise<IStdCodesGroupProp> {
    return new Promise<IStdCodesGroupProp>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
