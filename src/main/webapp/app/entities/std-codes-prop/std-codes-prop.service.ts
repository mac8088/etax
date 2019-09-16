import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IStdCodesProp } from '@/shared/model/std-codes-prop.model';

const baseApiUrl = 'api/std-codes-props';

export default class StdCodesPropService {
  public find(id: number): Promise<IStdCodesProp> {
    return new Promise<IStdCodesProp>(resolve => {
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

  public create(entity: IStdCodesProp): Promise<IStdCodesProp> {
    return new Promise<IStdCodesProp>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IStdCodesProp): Promise<IStdCodesProp> {
    return new Promise<IStdCodesProp>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
