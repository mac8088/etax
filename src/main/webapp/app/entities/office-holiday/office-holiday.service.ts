import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IOfficeHoliday } from '@/shared/model/office-holiday.model';

const baseApiUrl = 'api/office-holidays';

export default class OfficeHolidayService {
  public find(id: number): Promise<IOfficeHoliday> {
    return new Promise<IOfficeHoliday>(resolve => {
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

  public create(entity: IOfficeHoliday): Promise<IOfficeHoliday> {
    return new Promise<IOfficeHoliday>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IOfficeHoliday): Promise<IOfficeHoliday> {
    return new Promise<IOfficeHoliday>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
