import axios from 'axios';

import { IStdCodesGroup } from '@/shared/model/std-codes-group.model';

const baseApiUrl = 'api/std-codes-groups';

export default class StdCodesGroupService {
  public find(id: number): Promise<IStdCodesGroup> {
    return new Promise<IStdCodesGroup>(resolve => {
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

  public create(entity: IStdCodesGroup): Promise<IStdCodesGroup> {
    return new Promise<IStdCodesGroup>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IStdCodesGroup): Promise<IStdCodesGroup> {
    return new Promise<IStdCodesGroup>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
