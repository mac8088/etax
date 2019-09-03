import axios from 'axios';

import { IPublicHoliday } from '@/shared/model/public-holiday.model';

const baseApiUrl = 'api/public-holidays';

export default class PublicHolidayService {
  public find(id: number): Promise<IPublicHoliday> {
    return new Promise<IPublicHoliday>(resolve => {
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

  public create(entity: IPublicHoliday): Promise<IPublicHoliday> {
    return new Promise<IPublicHoliday>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPublicHoliday): Promise<IPublicHoliday> {
    return new Promise<IPublicHoliday>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
