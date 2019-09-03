import axios from 'axios';

import { IUserInfo } from '@/shared/model/user-info.model';

const baseApiUrl = 'api/user-infos';

export default class UserInfoService {
  public find(id: number): Promise<IUserInfo> {
    return new Promise<IUserInfo>(resolve => {
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

  public create(entity: IUserInfo): Promise<IUserInfo> {
    return new Promise<IUserInfo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IUserInfo): Promise<IUserInfo> {
    return new Promise<IUserInfo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
