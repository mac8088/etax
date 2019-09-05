import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IOfficeWeekday } from '@/shared/model/office-weekday.model';

const baseApiUrl = 'api/office-weekdays';

export default class OfficeWeekdayService {
  public find(id: number): Promise<IOfficeWeekday> {
    return new Promise<IOfficeWeekday>(resolve => {
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

  public create(entity: IOfficeWeekday): Promise<IOfficeWeekday> {
    return new Promise<IOfficeWeekday>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IOfficeWeekday): Promise<IOfficeWeekday> {
    return new Promise<IOfficeWeekday>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
