import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IBankAccount } from '@/shared/model/bank-account.model';

const baseApiUrl = 'api/bank-accounts';

export default class BankAccountService {
  public find(id: number): Promise<IBankAccount> {
    return new Promise<IBankAccount>(resolve => {
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

  public create(entity: IBankAccount): Promise<IBankAccount> {
    return new Promise<IBankAccount>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IBankAccount): Promise<IBankAccount> {
    return new Promise<IBankAccount>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
