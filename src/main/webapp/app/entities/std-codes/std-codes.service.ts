import axios from 'axios';

import { IStdCodes } from '@/shared/model/std-codes.model';

const baseApiUrl = 'api/std-codes';

export default class StdCodesService {
  public find(id: number): Promise<IStdCodes> {
    return new Promise<IStdCodes>(resolve => {
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

  public create(entity: IStdCodes): Promise<IStdCodes> {
    return new Promise<IStdCodes>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IStdCodes): Promise<IStdCodes> {
    return new Promise<IStdCodes>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
