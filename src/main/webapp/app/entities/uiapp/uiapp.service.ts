import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IUiapp } from '@/shared/model/uiapp.model';

const baseApiUrl = 'api/uiapps';

export default class UiappService {
  public find(id: number): Promise<IUiapp> {
    return new Promise<IUiapp>(resolve => {
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

  public create(entity: IUiapp): Promise<IUiapp> {
    return new Promise<IUiapp>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IUiapp): Promise<IUiapp> {
    return new Promise<IUiapp>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
