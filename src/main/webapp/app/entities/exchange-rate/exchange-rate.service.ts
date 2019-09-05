import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IExchangeRate } from '@/shared/model/exchange-rate.model';

const baseApiUrl = 'api/exchange-rates';

export default class ExchangeRateService {
  public find(id: number): Promise<IExchangeRate> {
    return new Promise<IExchangeRate>(resolve => {
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

  public create(entity: IExchangeRate): Promise<IExchangeRate> {
    return new Promise<IExchangeRate>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IExchangeRate): Promise<IExchangeRate> {
    return new Promise<IExchangeRate>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
