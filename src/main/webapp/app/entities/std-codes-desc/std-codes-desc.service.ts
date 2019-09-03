import axios from 'axios';

import { IStdCodesDesc } from '@/shared/model/std-codes-desc.model';

const baseApiUrl = 'api/std-codes-descs';

export default class StdCodesDescService {
  public find(id: number): Promise<IStdCodesDesc> {
    return new Promise<IStdCodesDesc>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl).then(function(res) {
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

  public create(entity: IStdCodesDesc): Promise<IStdCodesDesc> {
    return new Promise<IStdCodesDesc>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IStdCodesDesc): Promise<IStdCodesDesc> {
    return new Promise<IStdCodesDesc>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
